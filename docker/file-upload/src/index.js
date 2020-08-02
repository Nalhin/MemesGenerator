const express = require("express");
const fileUpload = require("express-fileupload");
const fs = require("fs");
const app = express();
const port = 3000;

app.use(fileUpload());

app.post("/upload", function (req, res) {
  if (!req.files || !req.files.image) {
    return res
      .status(400)
      .send({ message: "File not present", success: false });
  }
  const img = req.files.image;
  fs.writeFile(`/images/${img.name}`, img.data, (error) => {
    if (error) {
      return res
        .status(500)
        .send({ message: "File could not be saved", success: false });
    }
    console.log(`Saved ${img.name}`);
    res.status(201).send({ name: img.name, success: true });
  });
});

app.listen(port, "file_upload", () =>
  console.log(`File upload listening at port ${port}`)
);
