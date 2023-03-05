export class MealPrices {
    qaPrice !: number
    qcPrice !: number
    faPrice !: number
    fcPrice !: number
    snackPrice !: number

    constructor(qaPrice: number, qcPrice: number, faPrice: number,
        fcPrice: number, snackPrice: number) {
            this.qaPrice = qaPrice
            this.qcPrice = qaPrice
            this.faPrice = faPrice
            this.fcPrice = fcPrice
            this.snackPrice = snackPrice
    }
}
