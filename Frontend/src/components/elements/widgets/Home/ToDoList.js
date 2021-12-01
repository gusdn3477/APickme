export default function ToDoList() {
  return (
    <div className="col-md-5 grid-margin stretch-card">
      <div className="card">
        <div className="card-body">
          <h4 className="card-title">To Do Lists</h4>
          <div className="list-wrapper pt-2">
            <ul className="d-flex flex-column-reverse todo-list todo-list-custom">
              <li>
                <div className="form-check form-check-flat">
                  <label className="form-check-label">
                    <input className="checkbox" type="checkbox" />
                    Meeting with Urban Team
                  </label>
                </div>
                <i className="remove ti-close"></i>
              </li>
              <li className="completed">
                <div className="form-check form-check-flat">
                  <label className="form-check-label">
                    <input className="checkbox" type="checkbox" checked />
                    Duplicate a project for new customer
                  </label>
                </div>
                <i className="remove ti-close"></i>
              </li>
              <li>
                <div className="form-check form-check-flat">
                  <label className="form-check-label">
                    <input className="checkbox" type="checkbox" />
                    Project meeting with CEO
                  </label>
                </div>
                <i className="remove ti-close"></i>
              </li>
              <li className="completed">
                <div className="form-check form-check-flat">
                  <label className="form-check-label">
                    <input className="checkbox" type="checkbox" checked />
                    Follow up of team zilla
                  </label>
                </div>
                <i className="remove ti-close"></i>
              </li>
              <li>
                <div className="form-check form-check-flat">
                  <label className="form-check-label">
                    <input className="checkbox" type="checkbox" />
                    Level up for Antony
                  </label>
                </div>
                <i className="remove ti-close"></i>
              </li>
            </ul>
          </div>
          <div className="add-items d-flex mb-0 mt-2">
            <input type="text" className="form-control todo-list-input" placeholder="Add new task" />
            <button className="add btn btn-icon text-primary todo-list-add-btn bg-transparent"><i className="icon-circle-plus"></i></button>
          </div>
        </div>
      </div>
    </div>
  );
}