import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ScoreService } from 'app/entities/score/score.service';
import { Score } from 'app/entities/score/score.model';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  checkoutForm = new FormGroup({})
  currentScore:any = 0;
  public score:Score[] = [];
  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router,private scoreService:ScoreService,formBuilder: FormBuilder) {
    this.checkoutForm = formBuilder.group({
      score: ['', [Validators.required,Validators.pattern("^[0-9]*$"), Validators.min(0), Validators.max(50)]],
      offsetscore: ['0', [Validators.required,Validators.pattern("^[0-9]*$"), Validators.min(0), Validators.max(50)]],
      topNet: [false,[]],
      trash: [false,[]],
      trick: new FormControl('normal',Validators.required)
    });
  }



  ngOnInit(): void {

    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => {this.account = account;
        this.updateData();
      });

  }
  updateData():void{
    this.scoreService.getAllScores().subscribe((results)=>{
      this.score = results;
      this.score.forEach( (element) => { if(this.account!=null && element.login === this.account.login)
        {
          console.log("blah");
          if(element.totalscore){
        this.currentScore = element.totalscore/10;
      }}
        })
    })

  }
  onSubmit(): void {
    const tiles = parseInt(this.checkoutForm.value.score,10);
    const offsettiles = parseInt(this.checkoutForm.value.offsetscore,10);
    let multiplier = 1;
    if(this.checkoutForm.value.topNet )
    {
      ++multiplier;
    }

    if(this.checkoutForm.value.trick === 'behind'){
      multiplier +=.5;
    }else if(this.checkoutForm.value.trick === 'hook')
    {
      multiplier +=.75;
    }
    else if( this.checkoutForm.value.trick === 'between')
    {
      multiplier +=1;
    }

    const output = (multiplier*(tiles+.5*offsettiles)) * 10;
    console.log(output);
      this.scoreService.updateScore(output.toFixed(0)).subscribe((results)=>{
                                                                        console.log(results);
                                                                      });
      window.location.reload();
    }


  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
