import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AgentDetails } from 'src/app/classes/agent-details';
import { AgentService } from 'src/app/auth/agent.service';
import countriesData from '../../../assets/data/countryList.json';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginDetails } from 'src/app/classes/login-details';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ttms-agent-form',
  templateUrl: './ttms-agent-form.component.html',
  styleUrls: ['./ttms-agent-form.component.scss']
})
export class TtmsAgentFormComponent {

  constructor(private agentService : AgentService, private router: Router){}

  public countryList : string[] = countriesData.countries
  public isDuplicate : boolean = false
  public modalDisplay : string = "none"
  public loginDetails : LoginDetails = new LoginDetails("", "");

  //Valdiator for agentDetails
  agentDetails = new FormGroup({
    person: new FormGroup({
      firstname: new FormControl("", [
        Validators.required,
        Validators.maxLength(50)
      ]),
      lastname: new FormControl("", [
        Validators.required,
        Validators.maxLength(50)
      ]),
      birthDate: new FormControl(new Date(), [
        Validators.required
      ])
    }),
    contact: new FormGroup({
      email: new FormControl("", [
        Validators.required,
        Validators.maxLength(50)
      ]),
      primaryPhoneNumber: new FormControl("", [
        Validators.required,
        Validators.maxLength(25)
      ]),
      secondaryPhoneNumber: new FormControl("", [
        Validators.minLength(1),
        Validators.maxLength(25)
      ])
    }),
    address: new FormGroup({
      addressLine: new FormControl("", [
        Validators.required,
        Validators.maxLength(50)
      ]),
      postalCode: new FormControl("", [
        Validators.required,
        Validators.maxLength(50)
      ]),
      city: new FormControl("", [
        Validators.required,
        Validators.maxLength(50)
      ]),
      province: new FormControl("", [
        Validators.required,
        Validators.maxLength(50)
      ]),
      country: new FormControl("", [
        Validators.required,
        Validators.maxLength(50)
      ])
    })
  }) 

  submitAgentForm(){
    let newAgent = <AgentDetails>this.agentDetails.value
    let responsePromise = this.agentService.addAgent(newAgent)

    responsePromise.then(
      (response) => {
        if (response instanceof HttpErrorResponse){
			    let responseStatus = <HttpErrorResponse><unknown>response.status

        if(JSON.stringify(responseStatus) == "400")
        	this.isDuplicate = true

			  else if(JSON.stringify(responseStatus) == "401")
				  this.router.navigate(['/'])
        }
          
		    else{
          this.modalDisplay = "block"
          this.loginDetails = response
          //alert(`Username: ${response.username} \n Password: ${response.password}`)
        } 
      }
    )
  }

  closePopup(){ this.modalDisplay = "none" }
}
