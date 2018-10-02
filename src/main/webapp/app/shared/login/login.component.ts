import { Component, AfterViewInit, Renderer, ElementRef, Inject } from '@angular/core';
// import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
    selector: 'jhi-login-modal',
    templateUrl: './login.component.html'
})
export class JhiLoginModalComponent implements AfterViewInit {
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private router: Router,
        // public activeModal: NgbActiveModal,
        public dialog: MatDialog,
        public dialogRef: MatDialogRef<JhiLoginModalComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private spinner: NgxSpinnerService
    ) {
        this.credentials = {};
    }

    ngAfterViewInit() {
        // Estava dando erro de rederer is null
        // setTimeout(() => this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []), 0);
    }

    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
        // this.activeModal.dismiss('cancel');
    }

    login() {
        this.spinner.show();
        this.loginService
            .login({
                username: this.username,
                password: this.password,
                rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;
                // this.activeModal.dismiss('login success');
                if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.router.navigate(['']);
                    setTimeout(() => {
                        this.spinner.hide();
                    }, 3000);
                }

                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });

                // // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // // since login is succesful, go to stored previousState and clear previousState
                const redirect = this.stateStorageService.getUrl();
                if (redirect) {
                    this.stateStorageService.storeUrl(null);
                    this.router.navigate([redirect]);
                }
                setTimeout(() => {
                    this.spinner.hide();
                }, 3000);
                this.closeDialog();
            })
            .catch(() => {
                this.authenticationError = true;
                setTimeout(() => {
                    this.spinner.hide();
                }, 1000);
            });
    }

    public closeDialog() {
        setTimeout(() => {
            this.spinner.hide();
        }, 1000);
        this.dialogRef.close();
    }

    register() {
        // this.activeModal.dismiss('to state register');
        this.router.navigate(['/register']);
    }

    requestResetPassword() {
        // this.activeModal.dismiss('to state requestReset');
        this.router.navigate(['/reset', 'request']);
        this.dialogRef.close();
    }
}
