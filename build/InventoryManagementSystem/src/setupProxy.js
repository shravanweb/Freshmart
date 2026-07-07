const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    "/api",
    createProxyMiddleware({
      target: "http://127.0.0.1:8080",
      changeOrigin: true,
      ws: true,
      proxyTimeout: 120000,
      timeout: 120000,
      onError: (err, _req, res) => {
        if (res && typeof res.writeHead === "function" && !res.headersSent) {
          res.writeHead(503, { "Content-Type": "application/json" });
          res.end(
            JSON.stringify({
              errors: [
                {
                  message:
                    "Backend server is unavailable. Start Spring Boot on port 8080 and try again.",
                },
              ],
            })
          );
        }
        console.error("[proxy] /api request failed:", err.message);
      },
    })
  );
};
