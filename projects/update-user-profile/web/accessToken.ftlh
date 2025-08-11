[#ftl output_format="HTML"]
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Access Token Input
	</title><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"></head>
<body>

  
	
	
	
	
  
  
	<div class="container">
	  <div class="header">
		<i class="fas fa-key">
		</i> Enter Access Token
	  </div>
	  <div class="content">
		<form method="post" id="tokenForm">
		  <div class="form-group">
			<label class="form-label">Access Token:</label>
			<input type="text" id="accessToken" name="accessToken" required="" placeholder="Paste your access token here" class="form-control">
		  </div>
		  <div class="buttons">
			<button type="submit" class="btn btn-primary">Submit</button>
		  </div>
		  <div id="errorMessage" class="error">
		  </div>
		</form>
	  </div>
	</div>
  
  
  
</body>
<style>
	body {
	  font-family: Arial, sans-serif;
	  background-color: #f8f9fa;
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  height: 100vh;
	  margin: 0;
	}
	.container {
	  width: 100%;
	  max-width: 500px;
	  background: white;
	  border-radius: 5px;
	  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	  overflow: hidden;
	}
	.header {
	  background: #007bff;
	  padding: 15px;
	  color: white;
	  font-size: 20px;
	  font-weight: bold;
	  display: flex;
	  align-items: center;
	}
	.header i {
	  margin-right: 10px;
	}
	.content {
	  padding: 20px;
	}
	.form-group {
	  margin-bottom: 15px;
	}
	.form-label {
	  font-weight: bold;
	  display: block;
	  margin-bottom: 5px;
	}
	.form-control {
	  width: 95%;
	  padding: 10px;
	  font-size: 14px;
	  border: 1px solid #ced4da;
	  border-radius: 5px;
	}
	.buttons {
	  display: flex;
	  gap: 10px;
	}
	.btn {
	  padding: 10px 15px;
	  font-size: 16px;
	  border: none;
	  border-radius: 5px;
	  cursor: pointer;
	}
	.btn-primary {
	  background-color: #007bff;
	  color: white;
	}
	.btn-primary:hover {
	  background-color: #0056b3;
	}
	.error {
	  color: red;
	  font-weight: bold;
	  margin-top: 5px;
	}
  </style>
<script>
	document.getElementById('tokenForm').addEventListener('submit', function(e) {
	  e.preventDefault();
	  const token = document.getElementById('accessToken').value.trim();
	  const errorMsg = document.getElementById('errorMessage');
	  if (!token) {
		errorMsg.textContent = "Access token is required!";
		return;
	  }
	  errorMsg.textContent = "";
	  alert("Access Token Submitted:\n" + token);
	  // You can send it to your backend here using fetch()
	}
														 );
  </script>
</html>