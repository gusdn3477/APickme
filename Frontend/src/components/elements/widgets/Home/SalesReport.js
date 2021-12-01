export default function SalesReport() {
    return (
        <div className="col-md-6 grid-margin stretch-card">
            <div className="card">
                <div className="card-body">
                    <div className="d-flex justify-content-between">
                        <p className="card-title">Sales Report</p>
                        <a href="#" className="text-info">View all</a>
                    </div>
                    <p className="font-weight-500">The total number of sessions within the date range. It is the period time a user is actively engaged with your website, page or app, etc</p>
                    <div id="sales-legend" className="chartjs-legend mt-4 mb-2"></div>
                    <canvas id="sales-chart"></canvas>
                </div>
            </div>
        </div>
    );
}