import {Component, OnInit, Injectable} from '@angular/core';
import {Http} from "@angular/http";
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/catch';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
@Injectable()
export class AppComponent implements OnInit {
  title = 'app';

  constructor(private http: Http) {

  }

  ngOnInit(): void {

    this.http.get("http://localhost:9000/health")
      .toPromise()
      .then(response => console.log(JSON.stringify(response.json())))
      .catch(this.handleError);

    this.http.put("http://localhost:9000/putmoney", "{}")
      .toPromise()
      .then(response => console.log(JSON.stringify(response.json())))
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error)
    return Promise.reject(error.message || error)
  }
}
