import { Link, useHistory } from "react-router-dom";

export default function NotificationMenu() {

    const gogo = useHistory();
    return (
        <li className="nav-item dropdown">
            <a className="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#" data-bs-toggle="dropdown">
                <i className="icon-bell mx-0"></i>
                <span className="count"></span>
            </a>
            <div className="dropdown-menu dropdown-menu-right navbar-dropdown preview-list" aria-labelledby="notificationDropdown">
                <p className="mb-0 font-weight-normal float-left dropdown-header">Notifications</p>
                <a className="dropdown-item preview-item">
                    <div className="preview-thumbnail">
                        <div className="preview-icon bg-success">
                            <i className="ti-info-alt mx-0"></i>
                        </div>
                    </div>
                    <div className="preview-item-content">
                        <h6 className="preview-subject font-weight-normal">Application Error</h6>
                        <p className="font-weight-light small-text mb-0 text-muted">
                            Just now
                        </p>
                    </div>
                </a>
                <a className="dropdown-item preview-item">
                    <div className="preview-thumbnail">
                        <div className="preview-icon bg-warning">
                            <i className="ti-settings mx-0"></i>
                        </div>
                    </div>
                    <div className="preview-item-content">
                        <h6 className="preview-subject font-weight-normal">Settings</h6>
                        <p className="font-weight-light small-text mb-0 text-muted">
                            Private message
                        </p>
                    </div>
                </a>
                <a className="dropdown-item preview-item">
                    <div className="preview-thumbnail">
                        <div className="preview-icon bg-info">
                            <i className="ti-user mx-0"></i>
                        </div>
                    </div>
                    <div className="preview-item-content">
                        <h6 className="preview-subject font-weight-normal">New user registration</h6>
                        <p className="font-weight-light small-text mb-0 text-muted">
                            2 days ago
                        </p>
                    </div>
                </a>
            </div>
        </li>
    );

}