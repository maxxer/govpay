webpackJsonp([1],{0:function(t,e,n){t.exports=n("x35b")},"5xMp":function(t,e){t.exports='<mat-toolbar color="primary" class="mat-elevation-z1">\n  <mat-toolbar-row class="pt-3 align-items-start">\n    <span class="mat-toolbar-header">Govpay</span>\n  </mat-toolbar-row>\n</mat-toolbar>\n<div class="container-fluid p-3 inner-container">\n  <div class="row">\n    <div class="col-sm-4 col-12 flex-column form-col">\n      <mat-card class="mat-elevation-card">\n        <mat-card-header>\n          <mat-card-title>Autenticazione</mat-card-title>\n        </mat-card-header>\n        <mat-card-content>\n          <form action="j_security_check" method=post>\n            <mat-form-field class="w-100" color="accent">\n              <input id="inputEmail" matInput placeholder="Nome utente"\n                     name="j_username" required autofocus>\n            </mat-form-field>\n            <mat-form-field class="w-100" color="accent">\n              <input id="inputPassword" matInput placeholder="Password" type="password"\n                     name="j_password" required>\n            </mat-form-field>\n          </form>\n        </mat-card-content>\n        <mat-card-actions>\n          <button mat-button mat-raised-button color="accent" class="mat-elevation-z0" type="submit">ACCEDI</button>\n        </mat-card-actions>\n      </mat-card>\n      <mat-card class="my-3 mat-elevation-card">\n        <mat-card-header>\n          <mat-card-title>Govpay</mat-card-title>\n        </mat-card-header>\n        <mat-card-content>\n          <ul>\n            <li class="accent-text">\n              <a class="pt-1 d-block" href="" target="_blank">Manuale utente</a>\n              <a class="pt-1 d-block" href="" target="_blank">Copyright</a>\n              <a class="pt-1 d-block" href="" target="_blank">Progetto Govpay</a>\n            </li>\n          </ul>\n        </mat-card-content>\n      </mat-card>\n    </div>\n    <div class="col">\n      <mat-card class="flex-grow-1 mat-elevation-card">\n        <mat-card-header>\n          <mat-card-title>Notizie</mat-card-title>\n        </mat-card-header>\n        <mat-card-content>\n          <div *ngFor="let _news of news">\n            <div class="py-3 news-title"><a [href]="_news.html_url" target="_blank">{{_news.tag_name}} {{_news.name}}</a></div>\n            <div class="news-body" [innerHTML]="_news.body_html"></div>\n          </div>\n          <div *ngIf="news.length==0">Nessuna notizia.</div>\n        </mat-card-content>\n        <mat-spinner diameter="30" color="primary" class="news-spinner" *ngIf="_isLoading"></mat-spinner>\n      </mat-card>\n    </div>\n  </div>\n</div>\n'},Jnfr:function(t,e){function n(t){return Promise.resolve().then(function(){throw new Error("Cannot find module '"+t+"'.")})}n.keys=function(){return[]},n.resolve=n,t.exports=n,n.id="Jnfr"},okgc:function(t,e){t.exports=""},x35b:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n("WT6e"),o=n("4PVY"),r=n("OE0E"),c=n("Bj4q"),s=this&&this.__decorate||function(t,e,n,a){var o,r=arguments.length,c=r<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)c=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(o=t[s])&&(c=(r<3?o(c):r>3?o(e,n,c):o(e,n))||c);return r>3&&c&&Object.defineProperty(e,n,c),c},i=this&&this.__metadata||function(t,e){if("object"==typeof Reflect&&"function"==typeof Reflect.metadata)return Reflect.metadata(t,e)},l=function(){function t(t){this.sanitizer=t,this.news=[],this._isLoading=!1}return t.prototype.ngOnInit=function(){var t=new XMLHttpRequest;t.onreadystatechange=(function(){var e=this;4===t.readyState&&(200===t.status?(this._isLoading=!1,this.news=t.response?JSON.parse(t.response):[],this.news.forEach(function(t){t.body_html=e._trustHtml(t.body_html)})):(this._isLoading=!1,console.log("Error: "+t.status)))}).bind(this),this._isLoading=!0,t.open("GET","https://api.github.com/repos/link-it/GovPay/releases"),t.setRequestHeader("Accept","application/vnd.github.v3.html+json"),t.send()},t.prototype._trustHtml=function(t){return this.sanitizer.bypassSecurityTrustHtml(t)},t=s([Object(a.m)({selector:"app-root",template:n("5xMp"),styles:[n("okgc")]}),i("design:paramtypes",[r.c])],t)}(),d=n("j06o"),m=n("1OzB"),p=n("TBIh"),u=n("704W"),f=n("gsbp"),h=n("sqmn"),b=n("Bp8q"),v=this&&this.__decorate||function(t,e,n,a){var o,r=arguments.length,c=r<3?e:null===a?a=Object.getOwnPropertyDescriptor(e,n):a;if("object"==typeof Reflect&&"function"==typeof Reflect.decorate)c=Reflect.decorate(t,e,n,a);else for(var s=t.length-1;s>=0;s--)(o=t[s])&&(c=(r<3?o(c):r>3?o(e,n,c):o(e,n))||c);return r>3&&c&&Object.defineProperty(e,n,c),c},y=function(){function t(){}return t=v([Object(a.G)({declarations:[l],imports:[r.a,c.a,d.a,m.a,u.a,f.a,h.a,b.a,p.b],providers:[],bootstrap:[l]})],t)}();Object(a._10)(),Object(o.a)().bootstrapModule(y).catch(function(t){return console.log(t)})}},[0]);