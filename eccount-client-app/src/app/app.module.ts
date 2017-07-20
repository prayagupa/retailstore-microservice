import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {Http, HttpModule} from "@angular/http";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule, HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
