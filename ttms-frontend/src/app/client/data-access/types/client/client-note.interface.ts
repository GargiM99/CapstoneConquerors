export interface IClientNotes{
	noteTitle: string
	noteBody: string
	tripId: number | null
    clientId: number
}

export interface IClientNoteChange{
	clientNote: IClientNotes
	index: number | undefined
	newNote: boolean
}