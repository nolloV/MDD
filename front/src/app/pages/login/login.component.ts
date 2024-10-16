import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    loginForm: FormGroup; // Déclaration du formulaire de connexion

    // Constructeur pour injecter les services nécessaires
    constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
        // Initialisation du formulaire de connexion avec des validateurs
        this.loginForm = this.fb.group({
            identifier: ['', Validators.required], // Champ identifiant avec validation requise
            password: ['', Validators.required] // Champ mot de passe avec validation requise
        });
    }

    // Méthode appelée lors de la soumission du formulaire
    onSubmit(): void {
        if (this.loginForm.valid) { // Vérifier si le formulaire est valide
            this.authService.login(this.loginForm.value).subscribe(
                (response) => {
                    localStorage.setItem('token', response.token); // Stocker le token dans le localStorage
                    this.router.navigate(['/articles']); // Rediriger vers la page des articles
                },
                (error) => {
                    console.error('Login failed:', error); // Afficher une erreur en cas d'échec de connexion
                }
            );
        }
    }
}