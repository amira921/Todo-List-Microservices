import { NgModule } from '@angular/core';
import {RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./features/register/register.component";
import {LoginComponent} from "./features/login/login.component";

const appRoutes: Routes = [
    {path: '', component: LoginComponent},
    { path: 'register', component: RegisterComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule],
})

export class AppRoutingModule {}
