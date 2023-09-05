//This is an interface for modal components
import { EventEmitter } from "@angular/core";

export type ACTIONS = "SUBMIT" | "CLOSE"

export interface IModal{
    close(): any,
    submit?(): any,
    title?: string,
    inputValue?: any,
    closeEvent: EventEmitter<any>,
    submitEvent?: EventEmitter<any>
}

export interface ModalAction{
    action : ACTIONS,
    payload : any
}