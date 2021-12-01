export default function Notifications() {
  return (
    <div className="col-md-4 stretch-card grid-margin">
      <div className="card">
        <div className="card-body">
          <p className="card-title">Notifications</p>
          <ul className="icon-data-list">
            <li>
              <div className="d-flex">
                <img src="images/faces/face1.jpg" alt="user" />
                <div>
                  <p className="text-info mb-1">Isabella Becker</p>
                  <p className="mb-0">Sales dashboard have been created</p>
                  <small>9:30 am</small>
                </div>
              </div>
            </li>
            <li>
              <div className="d-flex">
                <img src="images/faces/face2.jpg" alt="user" />
                <div>
                  <p className="text-info mb-1">Adam Warren</p>
                  <p className="mb-0">You have done a great job #TW111</p>
                  <small>10:30 am</small>
                </div>
              </div>
            </li>
            <li>
              <div className="d-flex">
                <img src="images/faces/face3.jpg" alt="user" />
                <div>
                  <p className="text-info mb-1">Leonard Thornton</p>
                  <p className="mb-0">Sales dashboard have been created</p>
                  <small>11:30 am</small>
                </div>
              </div>
            </li>
            <li>
              <div className="d-flex">
                <img src="images/faces/face4.jpg" alt="user" />
                <div>
                  <p className="text-info mb-1">George Morrison</p>
                  <p className="mb-0">Sales dashboard have been created</p>
                  <small>8:50 am</small>
                </div>
              </div>
            </li>
            <li>
              <div className="d-flex">
                <img src="images/faces/face5.jpg" alt="user" />
                <div>
                  <p className="text-info mb-1">Ryan Cortez</p>
                  <p className="mb-0">Herbs are fun and easy to grow.</p>
                  <small>9:00 am</small>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}