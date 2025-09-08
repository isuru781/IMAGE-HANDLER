import React, { useState, useEffect } from 'react';
import axios from 'axios';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';

function App() {
  const [count, setCount] = useState(0);
  const [selectedFile, setSelectedFile] = useState(null);
  const [preview, setPreview] = useState(null);
  const [uploadedImages, setUploadedImages] = useState([]);
  const [uploadStatus, setUploadStatus] = useState('');

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setSelectedFile(file);
      setPreview(URL.createObjectURL(file));
    }
  };

  const handleUpload = async () => {
    if (!selectedFile) return;
    const formData = new FormData();
    formData.append('file', selectedFile);
    try {
      const res = await axios.post('http://localhost:8080/api/images/upload', formData);
      setUploadStatus(res.data);
      fetchImages();
    } catch (err) {
      setUploadStatus('Upload failed!');
    }
  };

  const fetchImages = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/images');
      setUploadedImages(res.data);
    } catch (err) {
      setUploadedImages([]);
    }
  };

  useEffect(() => {
    fetchImages();
  }, []);

  return (
    <>
      <div style={{ marginTop: '2rem' }}>
        <h2>Image Upload Test (Backend Connected)</h2>
        <input type="file" accept="image/*" onChange={handleImageChange} />
        {preview && (
          <div style={{ marginTop: '1rem' }}>
            <img src={preview} alt="Preview" style={{ maxWidth: '300px', maxHeight: '300px' }} />
            <br />
            <button onClick={handleUpload} style={{ marginTop: '1rem' }}>Upload</button>
          </div>
        )}
        {uploadStatus && <p>{uploadStatus}</p>}
      </div>

      <div style={{ marginTop: '2rem' }}>
        <h2>Uploaded Images</h2>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
          {uploadedImages.map(img => (
            <div key={img.id} style={{ border: '1px solid #ccc', padding: '1rem' }}>
              <img src={`http://localhost:8080/api/images/${img.id}`} alt={img.fileName} style={{ maxWidth: '150px', maxHeight: '150px' }} />
              <div>{img.fileName}</div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default App;

