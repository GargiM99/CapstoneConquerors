import { AbstractControl, ValidatorFn, ValidationErrors } from '@angular/forms';

export function regexValidator(pattern: RegExp): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value as string;

    if (!value) {
      return null;
    }

    const isValid = pattern.test(value);

    return isValid ? null : { regex: true };
  };
}
