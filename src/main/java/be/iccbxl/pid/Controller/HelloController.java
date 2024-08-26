package be.iccbxl.pid.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return """
        <!DOCTYPE html>
        <html lang="fr">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Accueil</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f5f5f5;
                    color: #333;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                    margin: 0;
                }
                .container {
                    text-align: center;
                    background-color: #ffffff;
                    padding: 20px;
                    border-radius: 10px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                }
                h1 {
                    font-size: 2.5rem;
                    color: #17a2b8;
                    margin-bottom: 20px;
                }
                p {
                    font-size: 1.2rem;
                    line-height: 1.5;
                }
                .btn-back {
                    margin-top: 20px;
                    padding: 10px 20px;
                    background-color: #17a2b8;
                    color: #fff;
                    border: none;
                    border-radius: 5px;
                    font-size: 1rem;
                    cursor: pointer;
                    text-decoration: none;
                }
                .btn-back:hover {
                    background-color: #138496;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>Greetings from Spring Boot!</h1>
                <p>Bienvenue sur l'application Spring Boot du moment</p>
                <button class="btn-back" onclick="history.back()">Retour</button>
            </div>
        </body>
        </html>
        """;
    }
}
