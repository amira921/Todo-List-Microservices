import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})

export class ValidationService {
  createFormGroup(fields: any[]): FormGroup {
    const formControls: { [key: string]: FormControl } = {};

    fields.forEach((field) => {
      const validators: any[] = [];
      if (field.validations) {
        field.validations.forEach((validation: any) => {
          const validatorFn = this.getValidatorFunction(validation);
          if (validatorFn) {
            validators.push(validatorFn);
          }
        });
      }
      formControls[field.controlName] = new FormControl('', validators);
    });
    return new FormGroup(formControls);
  }

  private getValidatorFunction(validation: any): any {
    switch (validation.validator) {
      case 'required':
        return Validators.required;
      case 'pattern':
        return validation.args ? Validators.pattern(validation.args[0] as string | RegExp) : null;
      case 'minLength':
        return validation.args ? Validators.minLength(validation.args[0] as number) : null;
      case 'maxLength':
        return validation.args ? Validators.maxLength(validation.args[0] as number) : null;
      default:
        return null;
    }
  }

  getErrorMessage(fieldName: string, errors: any, fields: any[]): string | null {
    const field = fields.find((f) => f.controlName === fieldName);
    if (!field || !errors) return null;

    for (const validation of field.validations) {
      if (errors[validation.validator]) return validation.message || 'Invalid field value.';
    }
    return null;
  }

}
