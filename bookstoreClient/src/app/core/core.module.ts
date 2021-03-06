import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import {
  HttpClientModule,
  HttpParams,
  HTTP_INTERCEPTORS
} from "@angular/common/http";
import { HttpTokenInterceptor } from "./interceptor/httpTokenInterceptor";
import { AuthenticationGuard } from "./guards/authentication.guard";
import { IsAdminGuard } from './guards/isAdmin.guard';
import {IsUserGuard} from './guards/isUser.guard';

@NgModule({
  declarations: [],
  imports: [CommonModule],
  exports: [HttpClientModule],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpTokenInterceptor, multi: true },
    HttpParams,
    Set,
    AuthenticationGuard,
    IsAdminGuard,
    IsUserGuard
  ]
})
export class CoreModule {}
