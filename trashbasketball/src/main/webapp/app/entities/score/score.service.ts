import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { Pagination } from 'app/core/request/request.model';
import { Score } from './score.model';

@Injectable({ providedIn: 'root' })
export class ScoreService {

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  public getAllScores(): Observable<Score[]> {
    return this.http.get<Score[]>(this.applicationConfigService.getEndpointFor('api/score/all'));
  }

  public updateScore(score:string): Observable<Score> {
    return this.http.put<Score>(this.applicationConfigService.getEndpointFor('api/score/update/')+score,null);
  }
}
