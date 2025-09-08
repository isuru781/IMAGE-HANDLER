# IMAGE-HANDLER

A full-stack image upload and viewing project with a React/Vite frontend and Spring Boot backend.

---

## API Endpoints

The backend exposes the following REST API endpoints under `/api/images`:

### 1. Upload Image

- **Endpoint:** `POST /api/images/upload`
- **Description:** Uploads an image file.
- **Request:** `multipart/form-data` with field `file`
- **Response:** Success message with uploaded image ID.

### 2. List Image Metadata

- **Endpoint:** `GET /api/images`
- **Description:** Returns metadata for all uploaded images.
- **Response:** JSON array of image objects.

### 3. Download/View Image by ID

- **Endpoint:** `GET /api/images/{id}`
- **Description:** Returns the raw image file for the given ID.
- **Response:** Image file stream with correct content type.

---

## Frontend Usage

- **Tech Stack:** React + Vite
- **Features:**
  - Upload local images (connected to backend)
  - Preview selected image before upload
  - List all uploaded images with thumbnails
  - Displays image filename
  - Fetches and renders images from backend API

---

## Getting Started

### Backend

1. **Requirements:** Java, Maven
2. **Run:**
   ```bash
   cd image\ handler\ backend
   mvn spring-boot:run
   ```
3. **Configuration:**
   - Adjust upload directory in `application.properties` if needed.
   - Backend runs on `localhost:8080` by default.

### Frontend

1. **Requirements:** Node.js, npm
2. **Run:**
   ```bash
   cd image\ handler\ frontend
   npm install
   npm run dev
   ```
3. **Configuration:** 
   - Frontend expects backend at `http://localhost:8080`
   - Change API base URLs in `src/App.jsx` if backend runs elsewhere.

---

## Example Flow

1. **Select an image file** using the file input.
2. **Preview** appears immediately.
3. **Click "Upload"** to send to backend.
4. **Uploaded images** are displayed below, fetched from `/api/images`.
5. **Thumbnails** use `GET /api/images/{id}` for the actual image content.

---

## API Reference

| Endpoint                 | Method | Body / Params      | Description                           |
|--------------------------|--------|--------------------|---------------------------------------|
| `/api/images/upload`     | POST   | file (form-data)   | Upload an image file                  |
| `/api/images`            | GET    | —                  | List metadata for all uploaded images |
| `/api/images/{id}`       | GET    | id (path)          | Get/download image file by ID         |

---

## Folder Structure

```
IMAGE-HANDLER/
│
├── image handler backend/      # Spring Boot backend
│   └── src/main/java/isuru/com/isuru/
├── image handler frontend/     # React + Vite frontend
│   └── src/
├── README.md
```

---

## License

MIT

---

## Author

[isuru781](https://github.com/isuru781)
