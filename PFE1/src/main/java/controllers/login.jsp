<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion - Parcours Patient</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow p-4 rounded-4">
                <h3 class="text-center mb-4">Connexion</h3>
                <form action="LoginServlet" method="post">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email :</label>
                        <input type="email" name="email" id="email" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Mot de passe :</label>
                        <input type="password" name="password" id="password" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label for="role" class="form-label">Rôle :</label>
                        <select name="role" id="role" class="form-select" required>
                            <option value="">-- Sélectionner --</option>
                            <option value="patient">Patient</option>
                            <option value="medecin">Médecin</option>
                            <option value="infirmier">Infirmier</option>
                            <option value="admin">Administratif</option>
                        </select>
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Se connecter</button>
                    </div>
                </form>

                <%
                    String error = request.getParameter("error");
                    if (error != null) {
                %>
                    <div class="alert alert-danger mt-3 text-center">
                        <%= error %>
                    </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>

</body>
</html>
