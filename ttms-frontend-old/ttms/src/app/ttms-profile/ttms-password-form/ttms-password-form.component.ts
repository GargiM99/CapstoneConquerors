import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-ttms-password-form',
  templateUrl: './ttms-password-form.component.html',
  styleUrls: ['./ttms-password-form.component.scss']
})
export class TtmsPasswordFormComponent {
  constructor(private profileService : ProfileService, private router : Router){}

  changePasswordForm = new FormGroup({  
    password: new FormControl("", [
      Validators.required,
      Validators.minLength(8),
      Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&-])[A-Za-z\\d@$!%*?&-]{8,}$')
    ]),
    confirmPassword: new FormControl("", [
      Validators.required
    ])
  })

  get password(): AbstractControl {return this.changePasswordForm.controls?.['password']}

  get confirmPassword(): AbstractControl {return this.changePasswordForm.controls?.['confirmPassword']}

  onSubmit(){
    let responsePromise = this.profileService.changePassword(this.password?.['value'])

    responsePromise.then(
      (response) => {
        if (response == 200)
          this.router.navigate(['/'])
        
        else
          alert("There was an error")
      }
    )
  }
}
