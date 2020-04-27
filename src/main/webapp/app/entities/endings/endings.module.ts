import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { UzKazNlpToolsSharedModule } from 'app/shared/shared.module';
import { EndingsComponent } from './endings.component';
import { EndingsDetailComponent } from './endings-detail.component';
import { EndingsUpdateComponent } from './endings-update.component';
import { EndingsDeleteDialogComponent } from './endings-delete-dialog.component';
import { endingsRoute } from './endings.route';

@NgModule({
  imports: [UzKazNlpToolsSharedModule, RouterModule.forChild(endingsRoute)],
  declarations: [EndingsComponent, EndingsDetailComponent, EndingsUpdateComponent, EndingsDeleteDialogComponent],
  entryComponents: [EndingsDeleteDialogComponent]
})
export class UzKazNlpToolsEndingsModule {}
