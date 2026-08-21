import React, { useEffect, useState } from 'react';
import { sectionsApi, programsApi, semestersApi, academicYearsApi } from '../api/endpoints';
import { Section, Program, Semester, AcademicYear } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Sections: React.FC = () => {
  const [sections, setSections] = useState<Section[]>([]);
  const [programs, setPrograms] = useState<Program[]>([]);
  const [semesters, setSemesters] = useState<Semester[]>([]);
  const [years, setYears] = useState<AcademicYear[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ name: '', capacity: 60, program: { id: 0 }, semester: { id: 0 }, academicYear: { id: 0 } });
  const [editId, setEditId] = useState<number | null>(null);
  const { isAdmin } = useAuth();
  const load = () => {
    sectionsApi.list().then(r => setSections(r.data));
    programsApi.list().then(r => setPrograms(r.data));
    semestersApi.list().then(r => setSemesters(r.data));
    academicYearsApi.list().then(r => setYears(r.data));
  };
  useEffect(() => { load(); }, []);
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editId) { await sectionsApi.update(editId, form); } else { await sectionsApi.create(form); }
    setShowForm(false); setEditId(null); load();
  };
  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Sections</h1><p className="page-subtitle">{sections.length} sections</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setEditId(null); setForm({ name: '', capacity: 60, program: { id: programs[0]?.id || 0 }, semester: { id: semesters[0]?.id || 0 }, academicYear: { id: years[0]?.id || 0 } }); }}>+ Add Section</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>{editId ? 'Edit' : 'Add'} Section</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group"><label>Section Name</label><input value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
              <div className="form-group"><label>Capacity</label><input type="number" value={form.capacity} onChange={e => setForm({...form, capacity: Number(e.target.value)})} /></div>
            </div>
            <div className="form-row">
              <div className="form-group"><label>Program</label><select value={form.program.id} onChange={e => setForm({...form, program: { id: Number(e.target.value) }})} required><option value="">Select</option>{programs.map(p => <option key={p.id} value={p.id}>{p.name}</option>)}</select></div>
              <div className="form-group"><label>Semester</label><select value={form.semester.id} onChange={e => setForm({...form, semester: { id: Number(e.target.value) }})} required><option value="">Select</option>{semesters.map(s => <option key={s.id} value={s.id}>Sem {s.semesterNumber}</option>)}</select></div>
            </div>
            <div className="form-group"><label>Academic Year</label><select value={form.academicYear.id} onChange={e => setForm({...form, academicYear: { id: Number(e.target.value) }})} required><option value="">Select</option>{years.map(y => <option key={y.id} value={y.id}>{y.yearName}</option>)}</select></div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">{editId ? 'Update' : 'Create'}</button></div>
          </form>
        </div>
      )}
      <div className="table-card">
        <table>
          <thead><tr><th>Name</th><th>Program</th><th>Semester</th><th>Year</th><th>Capacity</th>{isAdmin && <th>Actions</th>}</tr></thead>
          <tbody>
            {sections.map(s => (
              <tr key={s.id}>
                <td><span className="badge">{s.name}</span></td>
                <td>{s.program?.name}</td>
                <td>Semester {s.semester?.semesterNumber}</td>
                <td>{s.academicYear?.yearName}</td>
                <td>{s.capacity}</td>
                {isAdmin && <td><button className="btn-sm" onClick={() => { setForm(s); setEditId(s.id!); setShowForm(true); }}>Edit</button><button className="btn-sm danger" onClick={() => { if(confirm('Delete?')) { sectionsApi.delete(s.id!).then(load); } }}>Delete</button></td>}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
export default Sections;
