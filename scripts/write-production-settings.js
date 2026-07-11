const fs = require("fs");
const path = require("path");

const backendUrl = (process.env.BACKEND_URL || process.env.REACT_APP_BASE_HTTP_URL || "")
  .trim()
  .replace(/\/$/, "");

const wsUrl = (process.env.REACT_APP_BASE_WS_URL || "")
  .trim()
  .replace(/\/$/, "")
  || (backendUrl ? backendUrl.replace(/^http/i, "ws") : "");

const buildVersion = process.env.VERCEL_GIT_COMMIT_SHA
  ? process.env.VERCEL_GIT_COMMIT_SHA.slice(0, 7)
  : "";

const settings = {
  baseHttpUrl: backendUrl,
  baseWSurl: wsUrl,
  buildNumber: "1",
  buildVersion,
};

const appPublicDir = path.join(
  __dirname,
  "..",
  "build",
  "InventoryManagementSystem",
  "public"
);

const destination = path.join(appPublicDir, "resource", "settings.json");

fs.mkdirSync(path.dirname(destination), { recursive: true });
fs.writeFileSync(destination, `${JSON.stringify(settings, null, 2)}\n`);

// DomUtils.loadTheme expects IMSTheme{buildVersion}.css when buildVersion is set.
if (buildVersion) {
  const stylesDir = path.join(appPublicDir, "static", "styles");
  for (const baseName of ["IMSTheme", "base"]) {
    const source = path.join(stylesDir, `${baseName}.css`);
    const versioned = path.join(stylesDir, `${baseName}${buildVersion}.css`);
    if (fs.existsSync(source)) {
      fs.copyFileSync(source, versioned);
      console.log(`[deploy] Wrote ${path.relative(process.cwd(), versioned)}`);
    }
  }
}

console.log(
  `[deploy] Wrote ${path.relative(process.cwd(), destination)} with baseHttpUrl=${backendUrl || "(same origin)"}`
);
