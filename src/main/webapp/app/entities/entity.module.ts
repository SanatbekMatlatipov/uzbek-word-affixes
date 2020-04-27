import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'endings',
        loadChildren: () => import('./endings/endings.module').then(m => m.UzKazNlpToolsEndingsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class UzKazNlpToolsEntityModule {}
