import { TestBed } from '@angular/core/testing';

import { OldWayFormService } from './old-way-form.service';

describe('OldWayFormService', () => {
  let service: OldWayFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OldWayFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
