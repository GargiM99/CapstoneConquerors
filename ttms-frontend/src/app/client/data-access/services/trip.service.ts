import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Store, select } from '@ngrx/store';
import { IAppState } from 'src/app/share/data-access/types/app-state.interface';
import endPoints from '../../../../assets/data/endpoints.json'
import { Observable, map, mergeMap, switchMap } from 'rxjs';
import { ITripEventDetails } from '../types/trip/trip-event-details.interface';
import { detailSelector } from 'src/app/share/data-access/redux/auth/token-selectors';
import { ITokenDetail } from 'src/app/share/data-access/types/auth/token-details.interface';
import { ITripCreateDetails } from '../types/trip/create-trip-details.interface';
import { ITripType } from '../types/trip/trip-type.interface';
import { saveAs } from 'file-saver';
import { ITripDetails } from '../types/trip/trip-details.interface';

@Injectable({
  providedIn: 'root'
})
export class TripService {
  tokenDetails$: Observable<ITokenDetail | null>

  getTripEvent(tripId: number): Observable<ITripEventDetails>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }

        return this.sendGetTripEvent(tokenDetails.token, tripId)
      })
    )
  }

  sendGetTripEvent(token: String, tripId: number){
    let headers = new HttpHeaders({'Authorization': `Bearer ${token}`})

    let response = this.http.get<ITripEventDetails>(
      `${endPoints.trip}/${tripId}`, { headers: headers }
    )

    return response
  }

  createTrip(tripDetails: ITripCreateDetails): Observable<ITripEventDetails>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }

        return this.sendCreateTripEvent(tokenDetails.token, tripDetails)
      })
    )
  }

  sendCreateTripEvent(token: String, tripDetails: ITripCreateDetails){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    let response = this.http.post<ITripEventDetails>(
      `${endPoints.trip}`, tripDetails, { headers: headers }
    )

    return response
  }

  updateTrip(tripDetails: ITripEventDetails): Observable<ITripEventDetails>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }

        return this.sendUpdateTripEvent(tokenDetails.token, tripDetails)
      })
    )
  }

  sendUpdateTripEvent(token: String, tripDetails: ITripEventDetails){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })
    let response = this.http.put<ITripEventDetails>(
      `${endPoints.trip}`, tripDetails, { headers: headers }
    )

    return response
  }

  modifyTripType(tripType: ITripType[]): Observable<ITripType[]>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }

        return this.sendModifyTripType(tokenDetails.token, tripType)
      })
    )
  }

  sendModifyTripType(token: String, tripType: ITripType[]){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    })

    let response = this.http.post<ITripType[]>(
      `${endPoints.trip}/type`, tripType, { headers: headers }
    )

    return response
  }

  getTripType(): Observable<ITripType[]>{
    return this.tokenDetails$.pipe(
      mergeMap((tokenDetails) => {
        if (tokenDetails == null || tokenDetails.token == null){
          this.router.navigate(['login'])
          throw new Error("Session Expired")
        }

        return this.sendGetTripType(tokenDetails.token)
      })
    )
  }

  sendGetTripType(token: String){
    let headers = new HttpHeaders({'Authorization': `Bearer ${token}`})

    let response = this.http.get<ITripType[]>(
      `${endPoints.trip}/type`, { headers: headers }
    )

    return response
  }

  printTripSchedule(tripId: number) {
    this.tokenDetails$.subscribe((tokenDetails) => {
      if (tokenDetails == null || tokenDetails.token == null) {
        this.router.navigate(['login']);
        throw new Error("Session Expired");
      }
      this.sendPrintSchedule(tokenDetails.token, tripId);
    });
  }

  sendPrintSchedule(token: String, tripId: number){
    let headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Accept': 'application/pdf'
    })

    const responseType = 'arraybuffer';
    this.http.get(`${endPoints.trip}/report/${tripId}`, { headers, responseType })
      .subscribe((pdfData: ArrayBuffer) => {
        const blob = new Blob([pdfData], { type: 'application/pdf' });
        saveAs(blob, 'schedule.pdf'); 
    });
  }

  constructor(private store: Store<IAppState>, private http: HttpClient, private router: Router) {
    this.tokenDetails$ = this.store.pipe(select(detailSelector))
  }
} 
