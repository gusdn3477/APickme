export default function Projects() {
  return (
    <div className="col-md-4 stretch-card grid-margin">
      <div className="card">
        <div className="card-body">
          <p className="card-title mb-0">Projects</p>
          <div className="table-responsive">
            <table className="table table-borderless">
              <thead>
                <tr>
                  <th className="pl-0  pb-2 border-bottom">Places</th>
                  <th className="border-bottom pb-2">Orders</th>
                  <th className="border-bottom pb-2">Users</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td className="pl-0">Kentucky</td>
                  <td><p className="mb-0"><span className="font-weight-bold mr-2">65</span>(2.15%)</p></td>
                  <td className="text-muted">65</td>
                </tr>
                <tr>
                  <td className="pl-0">Ohio</td>
                  <td><p className="mb-0"><span className="font-weight-bold mr-2">54</span>(3.25%)</p></td>
                  <td className="text-muted">51</td>
                </tr>
                <tr>
                  <td className="pl-0">Nevada</td>
                  <td><p className="mb-0"><span className="font-weight-bold mr-2">22</span>(2.22%)</p></td>
                  <td className="text-muted">32</td>
                </tr>
                <tr>
                  <td className="pl-0">North Carolina</td>
                  <td><p className="mb-0"><span className="font-weight-bold mr-2">46</span>(3.27%)</p></td>
                  <td className="text-muted">15</td>
                </tr>
                <tr>
                  <td className="pl-0">Montana</td>
                  <td><p className="mb-0"><span className="font-weight-bold mr-2">17</span>(1.25%)</p></td>
                  <td className="text-muted">25</td>
                </tr>
                <tr>
                  <td className="pl-0">Nevada</td>
                  <td><p className="mb-0"><span className="font-weight-bold mr-2">52</span>(3.11%)</p></td>
                  <td className="text-muted">71</td>
                </tr>
                <tr>
                  <td className="pl-0 pb-0">Louisiana</td>
                  <td className="pb-0"><p className="mb-0"><span className="font-weight-bold mr-2">25</span>(1.32%)</p></td>
                  <td className="pb-0">14</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}