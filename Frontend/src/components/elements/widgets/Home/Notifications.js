export default function Charts() {
  return (
    <div className="col-md-4 stretch-card grid-margin">
      <div className="row">
        <div className="col-md-12 grid-margin stretch-card">
          <div className="card">
            <div className="card-body">
              <p className="card-title">Charts</p>
              <div className="charts-data">
                <div className="mt-3">
                  <p className="mb-0">Data 1</p>
                  <div className="d-flex justify-content-between align-items-center">

                    <p className="mb-0">5k</p>
                  </div>
                </div>
                <div className="mt-3">
                  <p className="mb-0">Data 2</p>
                  <div className="d-flex justify-content-between align-items-center">

                    <p className="mb-0">1k</p>
                  </div>
                </div>
                <div className="mt-3">
                  <p className="mb-0">Data 3</p>
                  <div className="d-flex justify-content-between align-items-center">

                    <p className="mb-0">992</p>
                  </div>
                </div>
                <div className="mt-3">
                  <p className="mb-0">Data 4</p>
                  <div className="d-flex justify-content-between align-items-center">

                    <p className="mb-0">687</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="col-md-12 stretch-card grid-margin grid-margin-md-0">
          <div className="card data-icon-card-primary">
            <div className="card-body">
              <p className="card-title text-white">Number of Meetings</p>
              <div className="row">
                <div className="col-8 text-white">
                  <h3>34040</h3>
                  <p className="text-white font-weight-500 mb-0">The total number of sessions within the date range.It is calculated as the sum . </p>
                </div>
                <div className="col-4 background-icon">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}