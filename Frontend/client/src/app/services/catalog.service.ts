import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CatalogService {
  private catalogApi = `/catalog/api/`;

  private catalogApiUser = `${environment.CATALOG_USER}`;
  private catalogApiPW = `${environment.CATALOG_PW}`;

  constructor( private http: HttpClient) {
    console.log({Catalog: {url: this.catalogApi, PW: this.catalogApiPW, User: this.catalogApiUser}});
   }

   getCatalog(): Observable<any>{
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(this.catalogApiUser + ':' + this.catalogApiPW)
    });

    return this.http.get<any>(this.catalogApi, {headers});
   }

   getItem(id: string): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(this.catalogApiUser + ':' + this.catalogApiPW)
    });

    return this.http.get<any>(this.catalogApi + id, {headers});
   }
}
