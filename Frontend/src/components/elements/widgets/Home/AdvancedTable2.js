export default function AdvancedTable2() {
  return (
    <div className="col-md-12 grid-margin stretch-card">
      <div className="card">
        <div className="card-body">
          <p className="card-title">면접대상자 목록</p>
          <div className="row">
            <div className="col-12">
              <div className="table-responsive">
                <table id="example" className="display expandable-table" style={{ width: "100%" }}>
                  <thead>
                    <tr>
                      <th>수험번호</th>
                      <th>이름</th>
                      <th>2차 면접관</th>
                      <th>2차 면점 점수</th>
                      <th>2차 면접 합/불</th>
                    </tr>
                  </thead>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}