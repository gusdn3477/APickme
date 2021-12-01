export default function OrderDetails() {
    return (
        <div className="col-md-6 grid-margin stretch-card">
            <div className="card">
                <div className="card-body">
                    <p className="card-title">Order Details</p>
                    <p className="font-weight-500">The total number of sessions within the date range. It is the period time a user is actively engaged with your website, page or app, etc</p>
                    <div className="d-flex flex-wrap mb-5">
                        <div className="mr-5 mt-3">
                            <p className="text-muted">Order value</p>
                            <h3 className="text-primary fs-30 font-weight-medium">12.3k</h3>
                        </div>
                        <div className="mr-5 mt-3">
                            <p className="text-muted">Orders</p>
                            <h3 className="text-primary fs-30 font-weight-medium">14k</h3>
                        </div>
                        <div className="mr-5 mt-3">
                            <p className="text-muted">Users</p>
                            <h3 className="text-primary fs-30 font-weight-medium">71.56%</h3>
                        </div>
                        <div className="mt-3">
                            <p className="text-muted">Downloads</p>
                            <h3 className="text-primary fs-30 font-weight-medium">34040</h3>
                        </div>
                    </div>
                    <canvas id="order-chart"></canvas>
                </div>
            </div>
        </div>
    );
}