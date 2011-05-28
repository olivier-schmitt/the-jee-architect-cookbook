package jee.architect.cookbook.misc.tomcat;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.util.ServerInfo;
import org.apache.catalina.valves.ValveBase;

/**
 * <p>A simple security valve : access to WEB-INF and META-INF prohibited.</p>
 * <p>Compliant with Tomcat 5.5.17, 5.5.23 (and probably more ...).</p>
 * <p>See Catalina source code for more examples.</p>
 *
 */
public class SecurityValve extends ValveBase {

    public void invoke(Request request, Response response) throws IOException, javax.servlet.ServletException {

        String contextPath = request.getRequestURI();

        if (contextPath.contains("/WEB-INF/")
                || contextPath.contains("/META-INF/")) {


            // The response is an error
            response.setError();

            try {
                response.reset();
            } catch (IllegalStateException e) {
                if (container.getLogger().isDebugEnabled()) {
                    container.getLogger().debug(e);
                }
            }

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            response.setSuspended(false);
            try {
                try {
                    response.setContentType("text/html");
                    response.setCharacterEncoding("utf-8");
                } catch (Throwable t) {
                    if (container.getLogger().isDebugEnabled()) {
                        container.getLogger().debug("status.setContentType", t);
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<html><head><title>");
                stringBuilder.append(ServerInfo.getServerInfo()).append(" - ");
                stringBuilder.append(sm.getString("errorReportValve.errorReport"));
                stringBuilder.append("</title>");
                stringBuilder.append("<style><!--");
                stringBuilder.append(org.apache.catalina.util.TomcatCSS.TOMCAT_CSS);
                stringBuilder.append("--></style> ");
                stringBuilder.append("</head><body>");
                stringBuilder.append("<h1>");
                stringBuilder.append(sm.getString("errorReportValve.statusHeader",
                        "" + 404, contextPath)).append("</h1>");
                stringBuilder.append("<HR size=\"1\" noshade=\"noshade\">");
                stringBuilder.append("<p><b>type</b> ");
                stringBuilder.append(sm.getString("errorReportValve.statusReport"));
                stringBuilder.append("</p>");
                stringBuilder.append("<p><b>");
                stringBuilder.append(sm.getString("errorReportValve.message"));
                stringBuilder.append("</b> <u>");
                stringBuilder.append(contextPath).append("</u></p>");
                stringBuilder.append("<p><b>");
                stringBuilder.append(sm.getString("errorReportValve.description"));
                stringBuilder.append("</b> <u>");
                stringBuilder.append(sm.getString("http.404", contextPath));
                stringBuilder.append("</u></p>");
                stringBuilder.append("<HR size=\"1\" noshade=\"noshade\">");
                stringBuilder.append("<h3>").append(ServerInfo.getServerInfo()).append("</h3>");
                stringBuilder.append("</body></html>");

                Writer writer = response.getReporter();
                if (writer != null) {
                    writer.write(stringBuilder.toString());
                }
            } catch (IOException e) {
                if (container.getLogger().isDebugEnabled()) {
                    container.getLogger().debug(e);
                }

            } catch (IllegalStateException e) {
                if (container.getLogger().isDebugEnabled()) {
                    container.getLogger().debug(e);
                }
            }
        } else {
            getNext().invoke(request, response);
        }
    }
}
