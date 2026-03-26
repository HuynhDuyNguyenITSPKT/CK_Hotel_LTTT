const toast = document.getElementById("toast");
const api = axios.create({baseURL: "/api/admin"});
let revenueChart;
let currentReportFilter = {day: null, month: null, year: null};

function showToast(message, ok = true) {
    toast.classList.remove("hidden", "border-rose-200", "bg-rose-50", "text-rose-700", "border-emerald-200", "bg-emerald-50", "text-emerald-700");
    toast.classList.add(ok ? "border-emerald-200" : "border-rose-200", ok ? "bg-emerald-50" : "bg-rose-50", ok ? "text-emerald-700" : "text-rose-700");
    toast.textContent = message;
}

function formatMoney(value) {
    return Number(value || 0).toLocaleString("vi-VN") + " VND";
}

function readReportFilter() {
    const dayValue = document.getElementById("report-day")?.value;
    const monthValue = document.getElementById("report-month")?.value;
    const yearValue = document.getElementById("report-year")?.value;
    return {
        day: dayValue ? Number(dayValue) : null,
        month: monthValue ? Number(monthValue) : null,
        year: yearValue ? Number(yearValue) : null
    };
}

function toQuery(params) {
    const query = new URLSearchParams();
    Object.entries(params)
        .filter(([, value]) => value !== null && value !== undefined && value !== "")
        .forEach(([key, value]) => query.append(key, String(value)));
    const encoded = query.toString();
    return encoded ? `?${encoded}` : "";
}

function updateFilterLabel(filter) {
    const label = document.getElementById("report-filter-label");
    if (!label) {
        return;
    }

    const pieces = [
        filter.day ? `Ngày ${filter.day}` : null,
        filter.month ? `Tháng ${filter.month}` : null,
        filter.year ? `Năm ${filter.year}` : null
    ].filter(Boolean);

    label.textContent = pieces.length ? `Đang xem: ${pieces.join(" - ")}` : "Đang xem: tất cả dữ liệu";
}

function paymentMethodBadge(method) {
    switch (method) {
        case "CASH":
            return "bg-emerald-100 text-emerald-700";
        case "CARD":
            return "bg-indigo-100 text-indigo-700";
        case "TRANSFER":
            return "bg-cyan-100 text-cyan-700";
        case "E_WALLET":
            return "bg-amber-100 text-amber-700";
        default:
            return "bg-slate-100 text-slate-600";
    }
}

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (!modal) {
        return;
    }
    modal.classList.remove("hidden");
    modal.classList.add("flex");
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (!modal) {
        return;
    }
    modal.classList.add("hidden");
    modal.classList.remove("flex");
}

function setInputValue(id, value) {
    const input = document.getElementById(id);
    if (input) {
        input.value = value ?? "";
    }
}

async function loadStats() {
    const {data} = await api.get("/dashboard/stats");
    setInputValue("stat-revenue", "");
    document.getElementById("stat-revenue").textContent = formatMoney(data.tongDoanhThu);
    document.getElementById("stat-invoices").textContent = data.totalHoaDon;
    document.getElementById("stat-customers").textContent = data.totalKhachHang;
    document.getElementById("stat-rooms").textContent = data.totalPhong;
}

async function loadRevenueChart() {
    const {data} = await api.get("/reports/revenue-chart");
    const canvas = document.getElementById("revenueChart");
    if (!canvas) {
        return;
    }

    const labels = data.labels || [];
    const values = (data.values || []).map(Number);

    if (revenueChart) {
        revenueChart.destroy();
    }

    revenueChart = new Chart(canvas, {
        type: "line",
        data: {
            labels,
            datasets: [{
                label: "Doanh thu (VND)",
                data: values,
                borderColor: "#4f46e5",
                backgroundColor: "rgba(79, 70, 229, 0.15)",
                fill: true,
                tension: 0.3
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {display: true}
            }
        }
    });
}

async function loadInvoiceReports() {
    const {data} = await api.get(`/reports/invoices${toQuery(currentReportFilter)}`);
    const tbody = document.getElementById("table-invoices");
    if (!tbody) {
        return;
    }

    tbody.innerHTML = data.map(item => `
        <tr class="border-b">
            <td class="p-2">#${item.hoaDonId}</td>
            <td class="p-2">${item.datPhongId ?? "-"}</td>
            <td class="p-2">${item.nhanVien || ""}</td>
            <td class="p-2 font-medium text-indigo-700">${formatMoney(item.tongTien)}</td>
            <td class="p-2 text-slate-500">${item.ngayTao ? new Date(item.ngayTao).toLocaleString("vi-VN") : ""}</td>
        </tr>
    `).join("");
}

async function loadPaymentReports() {
    const {data} = await api.get(`/reports/payments${toQuery(currentReportFilter)}`);
    const tbody = document.getElementById("table-payments");
    if (!tbody) {
        return;
    }

    tbody.innerHTML = data.map(item => `
        <tr class="border-b">
            <td class="p-2">#${item.thanhToanId}</td>
            <td class="p-2">#${item.hoaDonId ?? "-"}</td>
            <td class="p-2"><span class="rounded-full px-2 py-1 text-xs font-semibold ${paymentMethodBadge(item.phuongThuc)}">${item.phuongThuc}</span></td>
            <td class="p-2 font-medium text-emerald-700">${formatMoney(item.soTien)}</td>
            <td class="p-2 text-slate-500">${item.ngayThanhToan ? new Date(item.ngayThanhToan).toLocaleString("vi-VN") : ""}</td>
        </tr>
    `).join("");
}

async function downloadReport(url, fileName) {
    const response = await axios.get(url, {responseType: "blob"});
    const blobUrl = URL.createObjectURL(new Blob([response.data]));
    const anchor = document.createElement("a");
    anchor.href = blobUrl;
    anchor.download = fileName;
    document.body.appendChild(anchor);
    anchor.click();
    document.body.removeChild(anchor);
    URL.revokeObjectURL(blobUrl);
}

async function loadAccounts() {
    const {data} = await api.get("/accounts");
    document.getElementById("table-accounts").innerHTML = data.map(a => `
        <tr class="border-b">
            <td class="p-2">${a.username}</td>
            <td class="p-2">${a.email}</td>
            <td class="p-2">
                <select class="rounded border px-2 py-1" id="acc-${a.accountId}">
                    <option ${a.status === "ACTIVE" ? "selected" : ""} value="ACTIVE">ACTIVE</option>
                    <option ${a.status === "LOCKED" ? "selected" : ""} value="LOCKED">LOCKED</option>
                </select>
            </td>
            <td class="p-2">
                <button class="rounded bg-slate-900 px-2 py-1 text-white" data-action="save-account-status" data-id="${a.accountId}">Lưu</button>
            </td>
        </tr>
    `).join("");
}

async function saveAccountStatus(id) {
    await api.put(`/accounts/${id}/status`, {status: document.getElementById(`acc-${id}`).value});
    showToast("Cập nhật trạng thái tài khoản thành công");
}

async function loadEmployees() {
    const {data} = await api.get("/employees");
    document.getElementById("table-employees").innerHTML = data.map(e => `
        <tr class="border-b">
            <td class="p-2">${e.employeeName}</td>
            <td class="p-2">
                <select class="rounded border px-2 py-1" id="emp-${e.employeeId}">
                    <option ${e.role === "ADMIN" ? "selected" : ""}>ADMIN</option>
                    <option ${e.role === "MANAGER" ? "selected" : ""}>MANAGER</option>
                    <option ${e.role === "RECEPTIONIST" ? "selected" : ""}>RECEPTIONIST</option>
                </select>
            </td>
            <td class="p-2">${e.username}</td>
            <td class="p-2">${e.email}</td>
            <td class="p-2"><button class="rounded bg-slate-900 px-2 py-1 text-white" data-action="save-employee-role" data-id="${e.employeeId}">Lưu</button></td>
        </tr>
    `).join("");
}

async function saveEmployeeRole(id) {
    await api.put(`/employees/${id}/role`, {role: document.getElementById(`emp-${id}`).value});
    showToast("Cập nhật vai trò nhân viên thành công");
}

async function loadRooms() {
    const {data} = await api.get("/rooms");
    document.getElementById("table-rooms").innerHTML = data.map(r => `
        <tr class="border-b">
            <td class="p-2">${r.soPhong}</td>
            <td class="p-2">${r.tenLoaiPhong ?? ""}</td>
            <td class="p-2">${r.trangThai}</td>
            <td class="p-2 space-x-2">
                <button class="rounded bg-amber-500 px-2 py-1 text-white" data-action="open-room-edit"
                        data-id="${r.id}" data-sophong="${r.soPhong}" data-trangthai="${r.trangThai}" data-loaiphongid="${r.loaiPhongId}" data-imageurl="${r.imageUrl ?? ""}">Sửa</button>
                <button class="rounded bg-rose-600 px-2 py-1 text-white" data-action="delete-room" data-id="${r.id}">Xóa</button>
            </td>
        </tr>
    `).join("");
}

async function loadServices() {
    const {data} = await api.get("/services");
    document.getElementById("table-services").innerHTML = data.map(s => `
        <tr class="border-b">
            <td class="p-2">${s.ten}</td>
            <td class="p-2">${formatMoney(s.gia)}</td>
            <td class="p-2 space-x-2">
                <button class="rounded bg-amber-500 px-2 py-1 text-white" data-action="open-service-edit"
                        data-id="${s.id}" data-ten="${s.ten}" data-gia="${s.gia}" data-imageurl="${s.imageUrl ?? ""}">Sửa</button>
                <button class="rounded bg-rose-600 px-2 py-1 text-white" data-action="delete-service" data-id="${s.id}">Xóa</button>
            </td>
        </tr>
    `).join("");
}

async function loadPromotions() {
    const {data} = await api.get("/promotions");
    document.getElementById("table-promotions").innerHTML = data.map(p => `
        <tr class="border-b">
            <td class="p-2">${p.ten}</td>
            <td class="p-2">${p.loaiGiam}</td>
            <td class="p-2">${p.giaTri}</td>
            <td class="p-2 space-x-2">
                <button class="rounded bg-amber-500 px-2 py-1 text-white" data-action="open-promotion-edit"
                        data-id="${p.id}" data-ten="${p.ten}" data-loaigiam="${p.loaiGiam}" data-giatri="${p.giaTri}">Sửa</button>
                <button class="rounded bg-rose-600 px-2 py-1 text-white" data-action="delete-promotion" data-id="${p.id}">Xóa</button>
            </td>
        </tr>
    `).join("");
}

async function loadCustomers() {
    const {data} = await api.get("/customers");
    document.getElementById("table-customers").innerHTML = data.map(c => `
        <tr class="border-b">
            <td class="p-2">${c.ten}</td>
            <td class="p-2">${c.sdt}</td>
            <td class="p-2">${c.email ?? ""}</td>
            <td class="p-2 space-x-2">
                <button class="rounded bg-amber-500 px-2 py-1 text-white" data-action="open-customer-edit"
                        data-id="${c.id}" data-ten="${c.ten}" data-sdt="${c.sdt}" data-email="${c.email ?? ""}">Sửa</button>
                <button class="rounded bg-rose-600 px-2 py-1 text-white" data-action="delete-customer" data-id="${c.id}">Xóa</button>
            </td>
        </tr>
    `).join("");
}

async function deleteRoom(id) {
    await api.delete(`/rooms/${id}`);
    showToast("Xóa phòng thành công");
    await Promise.all([loadRooms(), loadStats()]);
}

async function deleteService(id) {
    await api.delete(`/services/${id}`);
    showToast("Xóa dịch vụ thành công");
    await Promise.all([loadServices(), loadStats()]);
}

async function deletePromotion(id) {
    await api.delete(`/promotions/${id}`);
    showToast("Xóa khuyến mãi thành công");
    await Promise.all([loadPromotions(), loadStats()]);
}

async function deleteCustomer(id) {
    await api.delete(`/customers/${id}`);
    showToast("Xóa khách hàng thành công");
    await Promise.all([loadCustomers(), loadStats()]);
}

function bindGlobalEvents() {
    document.querySelectorAll("[data-close]").forEach((button) => {
        button.addEventListener("click", () => closeModal(button.dataset.close));
    });

    document.addEventListener("click", async (event) => {
        const button = event.target.closest("button[data-action]");
        if (!button) {
            return;
        }

        const action = button.dataset.action;
        const id = Number(button.dataset.id);

        try {
            switch (action) {
                case "save-account-status":
                    await saveAccountStatus(id);
                    break;
                case "save-employee-role":
                    await saveEmployeeRole(id);
                    break;
                case "delete-room":
                    await deleteRoom(id);
                    break;
                case "delete-service":
                    await deleteService(id);
                    break;
                case "delete-promotion":
                    await deletePromotion(id);
                    break;
                case "delete-customer":
                    await deleteCustomer(id);
                    break;
                case "open-room-edit":
                    setInputValue("room-edit-id", button.dataset.id);
                    setInputValue("room-edit-soPhong", button.dataset.sophong);
                    setInputValue("room-edit-trangThai", button.dataset.trangthai);
                    setInputValue("room-edit-loaiPhongId", button.dataset.loaiphongid);
                    setInputValue("room-edit-imageUrl", button.dataset.imageurl);
                    openModal("modal-room");
                    break;
                case "open-service-edit":
                    setInputValue("service-edit-id", button.dataset.id);
                    setInputValue("service-edit-ten", button.dataset.ten);
                    setInputValue("service-edit-gia", button.dataset.gia);
                    setInputValue("service-edit-imageUrl", button.dataset.imageurl);
                    openModal("modal-service");
                    break;
                case "open-promotion-edit":
                    setInputValue("promotion-edit-id", button.dataset.id);
                    setInputValue("promotion-edit-ten", button.dataset.ten);
                    setInputValue("promotion-edit-loaiGiam", button.dataset.loaigiam);
                    setInputValue("promotion-edit-giaTri", button.dataset.giatri);
                    openModal("modal-promotion");
                    break;
                case "open-customer-edit":
                    setInputValue("customer-edit-id", button.dataset.id);
                    setInputValue("customer-edit-ten", button.dataset.ten);
                    setInputValue("customer-edit-sdt", button.dataset.sdt);
                    setInputValue("customer-edit-email", button.dataset.email);
                    openModal("modal-customer");
                    break;
                default:
                    break;
            }
        } catch (err) {
            const msg = err?.response?.data?.message || "Có lỗi khi thao tác dữ liệu";
            showToast(msg, false);
        }
    });

    document.querySelectorAll(".nav-btn").forEach((btn) => {
        btn.addEventListener("click", () => {
            document.querySelectorAll(".section-panel").forEach((section) => section.classList.add("hidden"));
            document.getElementById(btn.dataset.section)?.classList.remove("hidden");
            document.querySelectorAll(".nav-btn").forEach((item) => item.classList.remove("bg-slate-800"));
            btn.classList.add("bg-slate-800");
        });
    });

    document.querySelector(".nav-btn")?.classList.add("bg-slate-800");

    document.getElementById("btn-apply-filter")?.addEventListener("click", async () => {
        currentReportFilter = readReportFilter();
        updateFilterLabel(currentReportFilter);
        await Promise.all([loadInvoiceReports(), loadPaymentReports()]);
    });

    document.getElementById("btn-clear-filter")?.addEventListener("click", async () => {
        setInputValue("report-day", "");
        setInputValue("report-month", "");
        setInputValue("report-year", "");
        currentReportFilter = {day: null, month: null, year: null};
        updateFilterLabel(currentReportFilter);
        await Promise.all([loadInvoiceReports(), loadPaymentReports()]);
    });

    document.getElementById("export-invoices-excel")?.addEventListener("click", async () => {
        const query = toQuery(currentReportFilter);
        await downloadReport(`/api/admin/reports/invoices/export-excel${query}`, "bao-cao-hoa-don.xlsx");
    });

    document.getElementById("export-invoices-pdf")?.addEventListener("click", async () => {
        const query = toQuery(currentReportFilter);
        await downloadReport(`/api/admin/reports/invoices/export-pdf${query}`, "bao-cao-hoa-don.pdf");
    });

    document.getElementById("export-payments-excel")?.addEventListener("click", async () => {
        const query = toQuery(currentReportFilter);
        await downloadReport(`/api/admin/reports/payments/export-excel${query}`, "bao-cao-thanh-toan.xlsx");
    });

    document.getElementById("export-payments-pdf")?.addEventListener("click", async () => {
        const query = toQuery(currentReportFilter);
        await downloadReport(`/api/admin/reports/payments/export-pdf${query}`, "bao-cao-thanh-toan.pdf");
    });
}

function bindCreateForms() {
    document.getElementById("form-employee-create")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const payload = Object.fromEntries(new FormData(e.target).entries());
        await api.post("/employees", payload);
        e.target.reset();
        showToast("Tạo nhân viên thành công");
        await Promise.all([loadEmployees(), loadAccounts(), loadStats()]);
    });

    document.getElementById("form-room-create")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const payload = Object.fromEntries(new FormData(e.target).entries());
        payload.loaiPhongId = Number(payload.loaiPhongId);
        await api.post("/rooms", payload);
        e.target.reset();
        showToast("Thêm phòng thành công");
        await Promise.all([loadRooms(), loadStats()]);
    });

    document.getElementById("form-service-create")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const payload = Object.fromEntries(new FormData(e.target).entries());
        payload.gia = Number(payload.gia);
        await api.post("/services", payload);
        e.target.reset();
        showToast("Thêm dịch vụ thành công");
        await Promise.all([loadServices(), loadStats()]);
    });

    document.getElementById("form-promotion-create")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const payload = Object.fromEntries(new FormData(e.target).entries());
        payload.giaTri = Number(payload.giaTri);
        await api.post("/promotions", payload);
        e.target.reset();
        showToast("Thêm khuyến mãi thành công");
        await Promise.all([loadPromotions(), loadStats()]);
    });

    document.getElementById("form-customer-create")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const payload = Object.fromEntries(new FormData(e.target).entries());
        await api.post("/customers", payload);
        e.target.reset();
        showToast("Thêm khách hàng thành công");
        await Promise.all([loadCustomers(), loadStats()]);
    });
}

function bindEditForms() {
    document.getElementById("form-room-edit")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = Number(document.getElementById("room-edit-id").value);
        const payload = {
            soPhong: document.getElementById("room-edit-soPhong").value,
            trangThai: document.getElementById("room-edit-trangThai").value,
            loaiPhongId: Number(document.getElementById("room-edit-loaiPhongId").value),
            imageUrl: document.getElementById("room-edit-imageUrl").value
        };
        await api.put(`/rooms/${id}`, payload);
        closeModal("modal-room");
        showToast("Cập nhật phòng thành công");
        await loadRooms();
    });

    document.getElementById("form-service-edit")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = Number(document.getElementById("service-edit-id").value);
        const payload = {
            ten: document.getElementById("service-edit-ten").value,
            gia: Number(document.getElementById("service-edit-gia").value),
            imageUrl: document.getElementById("service-edit-imageUrl").value
        };
        await api.put(`/services/${id}`, payload);
        closeModal("modal-service");
        showToast("Cập nhật dịch vụ thành công");
        await loadServices();
    });

    document.getElementById("form-promotion-edit")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = Number(document.getElementById("promotion-edit-id").value);
        const payload = {
            ten: document.getElementById("promotion-edit-ten").value,
            loaiGiam: document.getElementById("promotion-edit-loaiGiam").value,
            giaTri: Number(document.getElementById("promotion-edit-giaTri").value)
        };
        await api.put(`/promotions/${id}`, payload);
        closeModal("modal-promotion");
        showToast("Cập nhật khuyến mãi thành công");
        await loadPromotions();
    });

    document.getElementById("form-customer-edit")?.addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = Number(document.getElementById("customer-edit-id").value);
        const payload = {
            ten: document.getElementById("customer-edit-ten").value,
            sdt: document.getElementById("customer-edit-sdt").value,
            email: document.getElementById("customer-edit-email").value
        };
        await api.put(`/customers/${id}`, payload);
        closeModal("modal-customer");
        showToast("Cập nhật khách hàng thành công");
        await loadCustomers();
    });
}

async function bootstrap() {
    try {
        bindGlobalEvents();
        bindCreateForms();
        bindEditForms();

        await Promise.all([
            loadStats(),
            loadRevenueChart(),
            loadInvoiceReports(),
            loadPaymentReports(),
            loadAccounts(),
            loadEmployees(),
            loadRooms(),
            loadServices(),
            loadPromotions(),
            loadCustomers()
        ]);
    } catch (err) {
        const msg = err?.response?.data?.message || "Không tải được dữ liệu dashboard";
        showToast(msg, false);
    }
}

bootstrap();
