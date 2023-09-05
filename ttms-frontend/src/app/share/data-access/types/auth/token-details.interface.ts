export interface ITokenDetail{
    token: string
    decoded: ITokenDecoded 
}

export interface ITokenDecoded{
    sub: string,
    iat: number,
    exp: number,
    role: TRoles
}

export type TRoles = 'ANY' | 'AGENT' | 'ADMIN'