export default function Bangalore() {
  return (
    <div className="col-md-6 grid-margin stretch-card">
      <div className="card tale-bg">
        <div className="card-people mt-auto">
          <img src="images/dashboard/people.svg" alt="people" />
          <div className="weather-info">
            <div className="d-flex">
              <div>
                <h2 className="mb-0 font-weight-normal"><i className="icon-sun mr-2"></i>31<sup>C</sup></h2>
              </div>
              <div className="ml-2">
                <h4 className="location font-weight-normal">Bangalore</h4>
                <h6 className="font-weight-normal">India</h6>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}