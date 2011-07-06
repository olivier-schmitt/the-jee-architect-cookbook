package jee.architect.cookbook.perf.tomcat.easyload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.valves.ValveBase;

/**
 * Easy Load valve !
 *
 */
public class EasyLoadValve extends ValveBase {

    private int maxLoadAvg = 1000;
    private static final int ONE_SECOND = 1000;
    private int timeout = 15 * ONE_SECOND;
    private Integer rejectedSession = 0;

    public int getTimeout() {
        return timeout;
    }

    public synchronized void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public double getMaxLoadAvg() {
        return maxLoadAvg;
    }

    public synchronized void setMaxLoadAvg(int maxLoadAvg) {
        this.maxLoadAvg = maxLoadAvg;
    }

    protected boolean hasSession(Request request) {
        return (request.getSession(false) != null);
    }

    public int getRejectedSession() {
        return rejectedSession;
    }

    public synchronized void setRejectedSession(int rejectedSession) {
        this.rejectedSession = rejectedSession;
    }

    protected double getLoadAvg() {

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double currentLoadAvg = osBean.getSystemLoadAverage();
        return currentLoadAvg;
    }

    protected void rejectSession(Request request, Response response) throws IOException, javax.servlet.ServletException {

        //container.getLogger().info("New session rejected.");

        synchronized (this) {
            this.rejectedSession++;
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("jee/architect/cookbook/perf/tomcat/easyload/toomuchload.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder pageBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            pageBuilder.append(line);
        }
        String page = pageBuilder.toString();

        String contextPath = request.getRequestURI();

        page = page.replace("URL", contextPath).replace("TIMEOUT", "" + this.timeout).replace("T_SECONDS", "" + ((int) (this.timeout / 1000)));

        // The response is an error
        response.setError();

        try {
            response.reset();
        } catch (IllegalStateException e) {
            ;
        }

        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setSuspended(false);
        try {
            try {
                response.setContentType("text/html");
                response.setCharacterEncoding("utf-8");
            } catch (Throwable t) {
                /*
                if (container.getLogger().isDebugEnabled()) {
                    container.getLogger().debug("status.setContentType", t);
                }
                 * 
                 */
            }
            Writer writer = response.getReporter();
            if (writer != null) {
                // If writer is null, it's an indication that the response has
                // been hard committed already, which should never happen
                writer.write(page);
            }
        } catch (IOException e) {
            ;
        } catch (IllegalStateException e) {
            ;
        }
    }

    public void invoke(Request request, Response response) throws IOException, javax.servlet.ServletException {

        // New session ?
        if (!hasSession(request)) {

            double curLoadAvg = getLoadAvg();

            // Too much load ????
            if (curLoadAvg > maxLoadAvg) {

                // Reject session !!!
                // The response is an error
                rejectSession(request, response);

            } else {
                // Max session reached ?
                Context context = request.getContext();
                StandardManager standardManager = (StandardManager) context.getManager();
                int maxActiveSessionsAllowed = standardManager.getMaxActiveSessions();
                // No, unlimited Session 
                if (maxActiveSessionsAllowed == -1) {

                    getNext().invoke(request, response);

                } else {
                    int activeSessions = standardManager.getActiveSessions();
                    if (activeSessions >= maxActiveSessionsAllowed) {

                        // Reject session !!!
                        // The response is an error
                        rejectSession(request, response);
                    } else {
                        getNext().invoke(request, response);
                    }
                }
            }
        } else {
            getNext().invoke(request, response);
        }
    }
}
