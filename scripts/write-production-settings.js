const fs = require("fs");
const path = require("path");

const backendUrl = (process.env.BACKEND_URL || process.env.REACT_APP_BASE_HTTP_URL || "")
  .trim()
  .replace(/\/$/, "");

const wsUrl = (process.env.REACT_APP_BASE_WS_URL || "")
  .trim()
  .replace(/\/$/, "")
  || (backendUrl ? backendUrl.replace(/^http/i, "ws") : "");

const settings = {
  baseHttpUrl: backendUrl,
  baseWSurl: wsUrl,
  buildNumber: "1",
  buildVersion: process.env.VERCEL_GIT_COMMIT_SHA
    ? process.env.VERCEL_GIT_COMMIT_SHA.slice(0, 7)
    : "",
};

const destination = path.join(
  __dirname,
  "..",
  "build",
  "InventoryManagementSystem",
  "public",
  "resource",
  "settings.json"
);

fs.mkdirSync(path.dirname(destination), { recursive: true });
fs.writeFileSync(destination, `${JSON.stringify(settings, null, 2)}\n`);

console.log(
  `[deploy] Wrote ${destination} with baseHttpUrl=${backendUrl || "(same origin)"}`
);
