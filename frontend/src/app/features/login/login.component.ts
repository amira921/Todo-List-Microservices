import { Component } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {LOGIN_FORM, REGISTRATION_FORM} from "../../statics/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {ValidationService} from "../../core/services/validation.service";
import {User} from "../../core/models/authentication.interface";
import {ErrorResponse} from "../../core/models/error.interface";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent{
  loginForm!: FormGroup;
  loginFields = LOGIN_FORM;
  message: String | null = null;
  apiUrl: string = "http://localhost:8081/auth/login";

  constructor(private http: HttpClient, private router: Router, private validationService: ValidationService) {
    this.loginForm = this.validationService.createFormGroup(this.loginFields);
  }

  onLogin(): void {
    if (!this.loginForm.valid) return;
    const user: User = this.loginForm.value;

    this.http.post(this.apiUrl, user, {responseType: 'text'}).subscribe({
      next: () => {
        this.router.navigate(['/home']);
      },
      error: (error) => {
        if (error.status == 403) {
          this.message = 'Account is inactive, please check your inbox for activation';
        } else if (error.status == 404) {
          this.message = 'Account is not found, please register first!';
        } else if (error.status == 401) {
          this.message = 'Password is incorrect, please try again!';
        }
      }
    });
  }

  getErrorMessage(fieldName: string): string | null {
    const control = this.loginForm.get(fieldName);
    if (control && control.invalid && (control.dirty || control.touched)) {
      return this.validationService.getErrorMessage(fieldName, control.errors, this.loginFields);
    }
    return null;
  }
}