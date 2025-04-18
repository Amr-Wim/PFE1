<%@ page language="java" contentType="text/html; charset=UTF-8" %>
 <!DOCTYPE html>
 <html>
 <head>
   <meta charset="UTF-8">
   <title>Prescription Médicale</title>
   <style>
     body {
       margin: 0;
       padding: 0;
       background: linear-gradient(135deg, #dbeafe, #e0f2fe); /* BLEU CLAIR */
       font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
       color: #1e3a5f;
       min-height: 100vh;
       display: flex;
       flex-direction: column;
     }
     header img {
       height: 50px;
     }
 
    
 
     main {
       flex: 1;
       display: flex;
       align-items: center;
       justify-content: center;
       padding: 40px 20px;
     }
 
     .form-box {
       background: #ffffff;
       color: #1e3a5f;
       padding: 40px;
       border-radius: 12px;
       width: 100%;
       max-width: 600px;
       box-shadow: 0 10px 30px rgba(0,0,0,0.1);
     }
 
     .form-box h2 {
       text-align: center;
       margin-bottom: 30px;
       color: #7e0021;
     }
 
     label {
   display: block;
   margin-bottom: 5px;
   font-weight: 700; /* gras */
   font-size: 15px;
 }
 
 label::after {
   content: " :";
 }
     
 
     input, select {
       width: 100%;
       padding: 10px 12px;
       border-radius: 8px;
       border: 1px solid #ccc;
       margin-bottom: 20px;
       font-size: 14px;
       background-color: #f8fafc;
     }
 
     .medicaments {
       margin-bottom: 20px;
     }
 
     .btn-add {
       background-color: #7e0021;
       color: white;
       padding: 8px 16px;
       border: none;
       border-radius: 8px;
       cursor: pointer;
       margin-bottom: 20px;
     }
 
     .btn-add:hover {
       background-color: #5e0019;
     }
 
     .btn-submit {
       width: 100%;
       padding: 12px;
       background-color: #1e3a5f;
       color: white;
       font-weight: bold;
       font-size: 16px;
       border: none;
       border-radius: 10px;
       cursor: pointer;
     }
 
     .btn-submit:hover {
       background-color: #16324b;
     }
     footer {
   background-color: #1e3a5f;
   color: #ffffff;
   text-align: center;
   padding: 16px 30px;
   font-size: 14px;
   border-top: 4px solid #7e0021;
   box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.15);
   font-weight: 500;
   letter-spacing: 0.5px;
 }
     
     header {
   background-color: #1e3a5f;
   padding: 20px 60px;
   display: flex;
   align-items: center;
   justify-content: flex-start;
   border-bottom: 4px solid #7e0021;
   box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
   color: white;
   position: relative;
   z-index: 10;
 }
 
 .logo-title {
   display: flex;
   align-items: center;
   gap: 20px;
 }
 
 .logo-title img {
   height: 48px;
   width: auto;
   filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.4));
 }
 
 .title {
   font-size: 30px;
   font-weight: 800;
   text-transform: uppercase;
   letter-spacing: 2px;
   color: #ffffff;
   background: none;
   text-shadow: 0 2px 4px rgba(126, 0, 33, 0.6);
 }
     
   </style>
 
   <script>
     function ajouterMedicament() {
       const container = document.getElementById("medicaments");
 
       const bloc = document.createElement("div");
       bloc.className = "medicaments";
 
       bloc.innerHTML = `
         <select name="medicament" required>
           <option value="">-- Choisir un médicament --</option>
         </select>
         <input type="text" name="dosage" placeholder="Dosage (ex: 500mg)" required>
         <input type="text" name="duree" placeholder="Durée (ex: 5 jours)" required>
       `;
 
       container.appendChild(bloc);
     }
   </script>
 </head>
 <body>
 
   <!-- Header -->
   <header>
     <div class="logo-title">
   <img src="image/image2.png" alt="Logo">
   <span class="title">CarePath</span>
 </div>
   </header>
 
   <!-- Formulaire -->
   <main>
     <div class="form-box">
       <h2>Nouvelle Prescription</h2>
       <form action="PrescriptionServlet" method="post">
         <label for="idPrescription">ID Prescription</label>
         <input type="text" name="idPrescription" id="idPrescription" required>
 
         <label for="datePrescription">Date de prescription</label>
         <input type="date" name="datePrescription" id="datePrescription" required>
 
         <label>Médicaments</label>
         <div id="medicaments">
           <div class="medicaments">
             <select name="medicament" required>
               <option value="">-- Choisir un médicament --</option>
             </select>
             <input type="text" name="dosage" placeholder="Dosage (ex: 500mg)" required>
             <input type="text" name="duree" placeholder="Durée (ex: 5 jours)" required>
           </div>
         </div>
 
         <button type="button" class="btn-add" onclick="ajouterMedicament()">+ Ajouter un médicament</button>
 
         <button type="submit" class="btn-submit">Valider la prescription</button>
       </form>
     </div>
   </main>
 
   <footer>
     &copy; 2025 - CarePath. Tous droits réservés.
   </footer>
 
 </body>
 </html>