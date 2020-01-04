<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="modal fade bd-example-modal-lg" id="createIssueModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header text-center">
				<h4 class="modal-title w-100 font-weight-bold">I S S U E</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body mx-3">
				<form action="Login.do" method="post" class="mt-5 mb-3 login-input">
					<div class="form-group row">
						<div class="col-2">Issue name</div>
						<div class="col-10">
							<input type="text" class="form-control input-default">
						</div>
					</div>
					<div class="form-group row">
						<div class="col-2">Column name</div>
						<div class="col-10">
							<select class="form-control">
								<option value="">Please select</option>
								<option value="to-do">to-do</option>
								<option value="done">done</option>
								<option value="doing">doing</option>
								<option value="delay">delay</option>
								<option value="development">development</option>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-2">Assignee</div>
						<div class="col-10">
							<i class="far fa-user-circle"></i> Unassigned
						</div>
					</div>
					<div class="form-group row">
						<div class="col-2">Due date</div>
						<div class="col-10">
							<div class="input-group">
								<input type="text" class="form-control mydatepicker"
									placeholder="mm/dd/yyyy"> <span
									class="input-group-append"> <span
									class="input-group-text"> <i
										class="mdi mdi-calendar-check"></i></span></span>
							</div>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-2">Projects</div>
						<div class="col-10">
							<select class="form-control">
								<option value="">Please select</option>
								<option value="1project">1project</option>
								<option value="2project">2project</option>
								<option value="3project">3project</option>
								<option value="4project">4project</option>
								<option value="5project">5project</option>
							</select>
						</div>
					</div>


					<input type="submit" class="btn login-form__btn submit w-100"
						value="Enroll">
				</form>
				<hr />


				<div class="container-fluid">
					<!-- row -->
					<div class="row">
						<div class="col-lg-12">
							<div class="card">
								<div class="card-body">
									<h4 class="card-title">
										<i class="far fa-comment"></i>&nbsp;&nbsp;Add Comment
									</h4>
									<div class="bootstrap-media">
										<div class="media">
											<img class="mr-3 img-fluid" src="images/avatar/1.jpg"
												alt="회원이미지">
											<div class="media-body">
												<h5 class="mt-0">Cathy</h5>
												Cras sit amet nibh libero, in gravida nulla. Nulla vel metus
												scelerisque ante sollicitudin.
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label>Comment:</label>
									<textarea class="form-control h-150px " rows="6" id="comment"
										placeholder="Ask a question or post an update">
						</textarea>
									<button type="button" class="btn mb-1 btn-primary"
										style="text-align: right">Comment</button>
								</div>
							</div>

						</div>



						<div class="example">
							<h5 class="box-title m-t-30">Default Datedpicker</h5>
							<p class="text-muted m-b-20">
								just add class
								<code>.mydatepicker</code>
								to create it.
							</p>
							<div class="input-group">
								<input type="text" class="form-control mydatepicker"
									placeholder="mm/dd/yyyy"> <span
									class="input-group-append"><span
									class="input-group-text"><i
										class="mdi mdi-calendar-check"></i></span></span>
							</div>
						</div>





					</div>
				</div>
			</div>
		</div>