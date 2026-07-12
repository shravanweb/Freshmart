# FreshMart — Inventory Management System

FreshMart is a grocery inventory and e-commerce platform with an admin back office and a public storefront. The app includes product catalog management, warehouse stock, purchase/sales orders, reports, and a customer-facing landing page.

## Live URLs

| Service | URL |
|---|---|
| Storefront (frontend) | https://freshmart-bice-one.vercel.app |
| API (backend) | https://freshmart-api-afm5.onrender.com |
| Health check | https://freshmart-api-afm5.onrender.com/actuator/health |

## Tech Stack

- **Frontend:** React 17, TypeScript, Apollo Client (GraphQL)
- **Backend:** Spring Boot, GraphQL, REST APIs
- **Database:** PostgreSQL
- **Deployment:** Vercel (frontend) + Render (backend + database)

## Project Structure

```
InventoryManagementSystem/
├── build/
│   ├── InventoryManagementSystem/   # React frontend
│   └── SpringBoot/                  # Spring Boot backend
├── scripts/
│   └── write-production-settings.js # Writes settings.json during Vercel build
├── vercel.json                      # Vercel deployment config
├── render.yaml                      # Render deployment config
└── README.md
```

D3E source definitions (`.d3e` files) live at the repo root for the D3E Studio model layer. Generated runtime code is under `build/`.

## Local Development

### Prerequisites

- Node.js 18+
- Java 17+
- PostgreSQL

### 1. Start the backend

```bash
cd build/SpringBoot
./gradlew bootRun
```

The API runs on **http://localhost:8080**.

Default database settings (override with environment variables if needed):

| Variable | Default |
|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/inventorymanagementsystem` |
| `SPRING_DATASOURCE_USERNAME` | `d3e` |
| `SPRING_DATASOURCE_PASSWORD` | `root` |

### 2. Start the frontend

```bash
cd build/InventoryManagementSystem
npm install
npm start
```

The app runs on **http://localhost:3000**. API requests to `/api/*` are proxied to port 8080 via `src/setupProxy.js`.

For local dev, leave `public/resource/settings.json` with empty `baseHttpUrl` so the frontend uses the same origin and the dev proxy.

## Deployment

Pushing to the `main` branch triggers automatic deploys on both Vercel and Render when those services are connected to the GitHub repo.

### Frontend (Vercel)

- Config: `vercel.json`
- Build command runs `scripts/write-production-settings.js`, which sets `baseHttpUrl` from the `BACKEND_URL` env var
- Output: `build/InventoryManagementSystem/build`

Required Vercel env var:

```
BACKEND_URL=https://freshmart-api-afm5.onrender.com
```

### Backend (Render)

- Config: `render.yaml`
- Docker build from `build/SpringBoot/Dockerfile`
- PostgreSQL database is provisioned via Render (`freshmart-db`)

Important Render env vars:

| Variable | Purpose |
|---|---|
| `DATABASE_URL` | Auto-set from Render Postgres |
| `JWT_SECRET` | Auth token signing |
| `APP_CORS_ALLOWED_ORIGINS` | Allowed frontend origins (e.g. `https://freshmart-bice-one.vercel.app`) |

### Manual deploy

```bash
git add .
git commit -m "Your message"
git push origin main
```

## Image Uploads

Product and category images are stored on the backend filesystem under `server.localstore` (default: `out/`).

| Type | Upload | Serve |
|---|---|---|
| Product images | `POST /api/product-image` | `GET /api/product-image/file?productId=...&filename=...` |
| Category images | `POST /api/category-image` | `GET /api/category-image/file?code=...` |

Upload category images from **Admin → Product Categories** when creating or editing a category.

After deploying backend changes, re-upload category images if they were uploaded before the API serving fix — older uploads may have been saved to a path the frontend could not reach.

Test a category image directly:

```
https://freshmart-api-afm5.onrender.com/api/category-image/file?code=YOUR_CATEGORY_CODE
```

## Key API Endpoints

| Endpoint | Description |
|---|---|
| `GET /api/public/categories` | Public category list for the storefront |
| `GET /api/public/products` | Public product list |
| `POST /api/category-image` | Upload category image (auth required) |
| `GET /api/category-image/file?code=...` | Serve category image |
| `POST /api/product-image` | Upload product image (auth required) |
| `GET /api/product-image/file?productId=...&filename=...` | Serve product image |
| `POST /api/native/graphql` | GraphQL API (admin) |

## Troubleshooting

**Category images not showing on the storefront**

- Confirm the backend deploy is live and the image URL uses `/api/category-image/file?code=...`
- Re-upload the image from the admin category form
- Check the direct API URL in the browser (see Image Uploads above)

**Frontend shows “Backend server is not responding”**

- Ensure Spring Boot is running on port 8080 locally
- In production, confirm `BACKEND_URL` is set correctly on Vercel

**CORS errors in production**

- Set `APP_CORS_ALLOWED_ORIGINS` on Render to your Vercel domain

**Styles broken after deploy**

- Vercel build copies versioned CSS via `write-production-settings.js`. Redeploy the frontend if `buildVersion` in `settings.json` does not match committed CSS files.

## License

Private project.
