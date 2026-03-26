const toast = document.getElementById("toast");
const api = axios.create({baseURL: "/api/admin"});

function showToast(message, ok = true) {
    toast.classList.remove("hidden", "border-rose-200", "bg-rose-50", "text-rose-700", "border-emerald-200", "bg-emerald-50", "text-emerald-700");
    toast.classList.add(ok ? "border-emerald-200" : "border-rose-200", ok ? "bg-emerald-50" : "bg-rose-50", ok ? "text-emerald-700" : "text-rose-700");
    toast.textContent = message;
}

function formatMoney(value) {
    return Number(value || 0).toLocaleString("vi-VN") + " VND";
}

async function loadStats() {
    const {data} = await api.get("/dashboard/stats");
    document.getElementById("stat-revenue").textContent = formatMoney(data.tongDoanhThu);
    document.getElementById("stat-invoices").textContent = data.totalHoaDon;
    document.getElementById("stat-customers").textContent = data.totalKhachHang;
    document.getElementById("stat-rooms").textContent = data.totalPhong;
}

async function loadAccounts() {
    const {data} = await api.get("/accounts");
    document.getElementById("table-accounts").innerHTML = data.map(a => `
        <tr class="border-b"><td class="p-2">${a.username}</td><td class="p-2">${a.email}</td>
        <td class="p-2"><select class="rounded border px-2 py-1" id="acc-${a.accountId}"><option ${a.status === "ACTIVE" ? "selected" : ""} value="ACTIVE">ACTIVE</option><option ${a.status === "LOCKED" ? "selected" : ""} value="LOCKED">LOCKED</option></select></td>
        <td class="p-2"><button class="rounded bg-slate-900 px-2 py-1 text-white" onclick="saveAccountStatus(${a.accountId})">Lưu</button></td></tr>
    `).join("");
}

async function saveAccountStatus(id) {
    await api.put(`/accounts/${id}/status`, {status: document.getElementById(`acc-${id}`).value});
    showToast("Cập nhật trạng thái tài khoản thành công");
}

async function loadEmployees() {
    const {data} = await api.get("/employees");
    document.getElementById("table-employees").innerHTML = data.map(e => `
        <tr class="border-b"><td class="p-2">${e.employeeName}</td><td class="p-2"><select class="rounded border px-2 py-1" id="emp-${e.employeeId}"><option ${e.role === "ADMIN" ? "selected" : ""}>ADMIN</option><option ${e.role === "MANAGER" ? "selected" : ""}>MANAGER</option><option ${e.role === "RECEPTIONIST" ? "selected" : ""}>RECEPTIONIST</option></select></td><td class="p-2">${e.username}</td><td class="p-2">${e.email}</td><td class="p-2"><button class="rounded bg-slate-900 px-2 py-1 text-white" onclick="saveEmployeeRole(${e.employeeId})">Lưu</button></td></tr>
    `).join("");
}

async function saveEmployeeRole(id) {
    await api.put(`/employees/${id}/role`, {role: document.getElementById(`emp-${id}`).value});
    showToast("Cập nhật vai trò nhân viên thành công");
}

async function loadRooms() {
    const {data} = await api.get("/rooms");
    document.getElementById("table-rooms").innerHTML = data.map(r => `
        <tr class="border-b"><td class="p-2">${r.soPhong}</td><td class="p-2">${r.tenLoaiPhong ?? ""}</td><td class="p-2">${r.trangThai}</td><td class="p-2 space-x-2"><button class="rounded bg-amber-500 px-2 py-1 text-white" onclick="editRoom(${r.id}, '${r.soPhong}', '${r.trangThai}', ${r.loaiPhongId}, '${r.imageUrl ?? ""}')">Sửa</button><button class="rounded bg-rose-600 px-2 py-1 text-white" onclick="deleteRoom(${r.id})">Xóa</button></td></tr>
    `).join("");
}

async function loadServices() {
    const {data} = await api.get("/services");
    document.getElementById("table-services").innerHTML = data.map(s => `<tr class="border-b"><td class="p-2">${s.ten}</td><td class="p-2">${formatMoney(s.gia)}</td><td class="p-2 space-x-2"><button class="rounded bg-amber-500 px-2 py-1 text-white" onclick="editService(${s.id}, '${s.ten}', ${s.gia}, '${s.imageUrl ?? ""}')">Sửa</button><button class="rounded bg-rose-600 px-2 py-1 text-white" onclick="deleteService(${s.id})">Xóa</button></td></tr>`).join("");
}

async function loadPromotions() {
    const {data} = await api.get("/promotions");
    document.getElementById("table-promotions").innerHTML = data.map(p => `<tr class="border-b"><td class="p-2">${p.ten}</td><td class="p-2">${p.loaiGiam}</td><td class="p-2">${p.giaTri}</td><td class="p-2 space-x-2"><button class="rounded bg-amber-500 px-2 py-1 text-white" onclick="editPromotion(${p.id}, '${p.ten}', '${p.loaiGiam}', ${p.giaTri})">Sửa</button><button class="rounded bg-rose-600 px-2 py-1 text-white" onclick="deletePromotion(${p.id})">Xóa</button></td></tr>`).join("");
}

async function loadCustomers() {
    const {data} = await api.get("/customers");
    document.getElementById("table-customers").innerHTML = data.map(c => `<tr class="border-b"><td class="p-2">${c.ten}</td><td class="p-2">${c.sdt}</td><td class="p-2">${c.email ?? ""}</td><td class="p-2 space-x-2"><button class="rounded bg-amber-500 px-2 py-1 text-white" onclick="editCustomer(${c.id}, '${c.ten}', '${c.sdt}', '${c.email ?? ""}')">Sửa</button><button class="rounded bg-rose-600 px-2 py-1 text-white" onclick="deleteCustomer(${c.id})">Xóa</button></td></tr>`).join("");
}

async function editRoom(id, soPhong, trangThai, loaiPhongId, imageUrl) {
    const nextSoPhong = prompt("Số phòng", soPhong);
    if (!nextSoPhong) return;
    const nextTrangThai = prompt("Trạng thái (AVAILABLE/OCCUPIED/MAINTENANCE/CLEANING)", trangThai);
    if (!nextTrangThai) return;
    const nextLoaiPhongId = prompt("ID loại phòng", loaiPhongId);
    if (!nextLoaiPhongId) return;
    const nextImage = prompt("Image URL", imageUrl ?? "") ?? "";
    await api.put(`/rooms/${id}`, {soPhong: nextSoPhong, trangThai: nextTrangThai, loaiPhongId: Number(nextLoaiPhongId), imageUrl: nextImage});
    showToast("Cập nhật phòng thành công");
    await loadRooms();
}

async function editService(id, ten, gia, imageUrl) {
    const nextTen = prompt("Tên dịch vụ", ten);
    if (!nextTen) return;
    const nextGia = prompt("Giá dịch vụ", gia);
    if (!nextGia) return;
    const nextImage = prompt("Image URL", imageUrl ?? "") ?? "";
    await api.put(`/services/${id}`, {ten: nextTen, gia: Number(nextGia), imageUrl: nextImage});
    showToast("Cập nhật dịch vụ thành công");
    await loadServices();
}

async function editPromotion(id, ten, loaiGiam, giaTri) {
    const nextTen = prompt("Tên khuyến mãi", ten);
    if (!nextTen) return;
    const nextLoai = prompt("Loại giảm (PERCENT/AMOUNT)", loaiGiam);
    if (!nextLoai) return;
    const nextGiaTri = prompt("Giá trị", giaTri);
    if (!nextGiaTri) return;
    await api.put(`/promotions/${id}`, {ten: nextTen, loaiGiam: nextLoai, giaTri: Number(nextGiaTri)});
    showToast("Cập nhật khuyến mãi thành công");
    await loadPromotions();
}

async function editCustomer(id, ten, sdt, email) {
    const nextTen = prompt("Tên khách hàng", ten);
    if (!nextTen) return;
    const nextSdt = prompt("Số điện thoại", sdt);
    if (!nextSdt) return;
    const nextEmail = prompt("Email", email ?? "") ?? "";
    await api.put(`/customers/${id}`, {ten: nextTen, sdt: nextSdt, email: nextEmail});
    showToast("Cập nhật khách hàng thành công");
    await loadCustomers();
}

async function deleteRoom(id) {
    await api.delete(`/rooms/${id}`);
    showToast("Xóa phòng thành công");
    await loadRooms();
    await loadStats();
}

async function deleteService(id) {
    await api.delete(`/services/${id}`);
    showToast("Xóa dịch vụ thành công");
    await loadServices();
    await loadStats();
}

async function deletePromotion(id) {
    await api.delete(`/promotions/${id}`);
    showToast("Xóa khuyến mãi thành công");
    await loadPromotions();
    await loadStats();
}

async function deleteCustomer(id) {
    await api.delete(`/customers/${id}`);
    showToast("Xóa khách hàng thành công");
    await loadCustomers();
    await loadStats();
}

document.getElementById("form-employee-create")?.addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = Object.fromEntries(new FormData(e.target).entries());
    await api.post("/employees", payload);
    e.target.reset();
    showToast("Tạo nhân viên thành công");
    await loadEmployees();
    await loadAccounts();
    await loadStats();
});

document.getElementById("form-room-create")?.addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = Object.fromEntries(new FormData(e.target).entries());
    payload.loaiPhongId = Number(payload.loaiPhongId);
    await api.post("/rooms", payload);
    e.target.reset();
    showToast("Thêm phòng thành công");
    await loadRooms();
    await loadStats();
});

document.getElementById("form-service-create")?.addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = Object.fromEntries(new FormData(e.target).entries());
    payload.gia = Number(payload.gia);
    await api.post("/services", payload);
    e.target.reset();
    showToast("Thêm dịch vụ thành công");
    await loadServices();
    await loadStats();
});

document.getElementById("form-promotion-create")?.addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = Object.fromEntries(new FormData(e.target).entries());
    payload.giaTri = Number(payload.giaTri);
    await api.post("/promotions", payload);
    e.target.reset();
    showToast("Thêm khuyến mãi thành công");
    await loadPromotions();
    await loadStats();
});

document.getElementById("form-customer-create")?.addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = Object.fromEntries(new FormData(e.target).entries());
    await api.post("/customers", payload);
    e.target.reset();
    showToast("Thêm khách hàng thành công");
    await loadCustomers();
    await loadStats();
});

document.querySelectorAll(".nav-btn").forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelectorAll(".section-panel").forEach(x => x.classList.add("hidden"));
        document.getElementById(btn.dataset.section)?.classList.remove("hidden");
        document.querySelectorAll(".nav-btn").forEach(b => b.classList.remove("bg-slate-800"));
        btn.classList.add("bg-slate-800");
    });
});

document.querySelector(".nav-btn")?.classList.add("bg-slate-800");

async function bootstrap() {
    try {
        await Promise.all([
            loadStats(),
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

