import React, { useEffect, useState } from 'react';
import { departmentsApi } from '../api/endpoints';
import { Department } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Departments: React.FC = () => {
  const [departments, setDepartments] = useState<Department[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<Department>({ code: '', name: '', description: '', active: true });
  const [editId, setEditId] = useState<number | null>(null);
  const { isAdmin } = useAuth();

  const load = () => departmentsApi.list().then(r => setDepartments(r.data));
  useEffect(() => { load(); }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editId) { await departmentsApi.update(editId, form); }
    else { await departmentsApi.create(form); }
    setShowForm(false); setEditId(null); setForm({ code: '', name: '', description: '', active: true });
    load();
  };

  const handleEdit = (d: Department) => { setForm(d); setEditId(d.id!); setShowForm(true); };
  const handleDelete = async (id: number) => { if (confirm('Delete?')) { await departmentsApi.delete(id); load(); } };

  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Departments</h1><p className="page-subtitle">{departments.length} departments</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setEditId(null); setForm({ code: '', name: '', description: '', active: true }); }}>+ Add Department</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>{editId ? 'Edit' : 'Add'} Department</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group"><label>Code</label><input value={form.code} onChange={e => setForm({...form, code: e.target.value})} required /></div>
              <div className="form-group"><label>Name</label><input value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
            </div>
            <div className="form-group"><label>Description</label><input value={form.description || ''} onChange={e => setForm({...form, description: e.target.value})} /></div>
            <div className="form-actions">
              <button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button>
              <button type="submit" className="btn-primary">{editId ? 'Update' : 'Create'}</button>
            </div>
          </form>
        </div>
      )}
      <div className="table-card">
        <table>
          <thead><tr><th>Code</th><th>Name</th><th>Description</th><th>Status</th>{isAdmin && <th>Actions</th>}</tr></thead>
          <tbody>
            {departments.map(d => (
              <tr key={d.id}>
                <td><span className="badge">{d.code}</span></td>
                <td><strong>{d.name}</strong></td>
                <td>{d.description}</td>
                <td><span className={`status ${d.active ? 'active' : 'inactive'}`}>{d.active ? 'Active' : 'Inactive'}</span></td>
                {isAdmin && <td><button className="btn-sm" onClick={() => handleEdit(d)}>Edit</button><button className="btn-sm danger" onClick={() => handleDelete(d.id!)}>Delete</button></td>}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Departments;
