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
    loginForm: FormGroup;

    constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
        this.loginForm = this.fb.group({
            identifier: ['', Validators.required],
            password: ['', Validators.required]
        });
    }

    onSubmit(): void {
        if (this.loginForm.valid) {
            this.authService.login(this.loginForm.value).subscribe(
                (response) => {
                    localStorage.setItem('token', response.token);
                    this.router.navigate(['/articles']); // Rediriger vers la page des articles
                },
                (error) => {
                    console.error('Login failed:', error);
                }
            );
        }
    }
}