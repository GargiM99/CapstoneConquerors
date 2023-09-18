import { AbstractControl, ValidationErrors } from "@angular/forms"
const DEFAULT_MAX_DATE = new Date()
const DEFAULT_MIN_DATE = new Date(1900, 0, 1)

export const minDateValidator = (control: AbstractControl, 
    minDate: Date = DEFAULT_MIN_DATE): ValidationErrors | null =>{
    
    const selectedDate: Date = new Date(control.value)
    if (selectedDate && selectedDate < minDate) 
        return null
    return { minDate: true }
}

export const maxDateValidator = (control: AbstractControl, 
    maxDate: Date = DEFAULT_MAX_DATE ): ValidationErrors | null => {
    
    const selectedDate: Date = new Date(control.value)
    if (selectedDate && selectedDate > maxDate) 
        return null
    return { maxDate: true }
}