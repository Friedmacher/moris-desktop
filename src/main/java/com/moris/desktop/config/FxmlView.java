package com.moris.desktop.config;

public enum FxmlView {
	LOGIN {
		@Override
		public String getFxmlPath() {
			return "/com/moris/desktop/security/login/LoginView.fxml";
		}
	},

	HOME {
		@Override
		public String getFxmlPath() {
			return "/com/moris/desktop/home/HomeView.fxml";
		}
	};

	public abstract String getFxmlPath();
}
