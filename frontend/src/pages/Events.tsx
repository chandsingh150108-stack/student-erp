import React, { useEffect, useState } from 'react';
import { eventsApi } from '../api/endpoints';
import { Event } from '../api/types';
import { useAuth } from '../context/AuthContext';
import './Page.css';

const Events: React.FC = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<any>({ name: '', description: '', startDate: '', endDate: '', venue: '', active: true });
  const [editId, setEditId] = useState<number | null>(null);
  const { isAdmin } = useAuth();
  const load = () => eventsApi.list().then(r => setEvents(r.data));
  useEffect(() => { load(); }, []);
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editId) { await eventsApi.update(editId, form); } else { await eventsApi.create(form); }
    setShowForm(false); setEditId(null); load();
  };
  return (
    <div className="page">
      <div className="page-header">
        <div><h1 className="page-title">Events</h1><p className="page-subtitle">{events.length} events</p></div>
        {isAdmin && <button className="btn-primary" onClick={() => { setShowForm(true); setEditId(null); setForm({ name: '', description: '', startDate: '', endDate: '', venue: '', active: true }); }}>+ Add Event</button>}
      </div>
      {showForm && (
        <div className="form-card">
          <h3>{editId ? 'Edit' : 'Add'} Event</h3>
          <form onSubmit={handleSubmit}>
            <div className="form-group"><label>Name</label><input value={form.name} onChange={e => setForm({...form, name: e.target.value})} required /></div>
            <div className="form-group"><label>Description</label><input value={form.description || ''} onChange={e => setForm({...form, description: e.target.value})} /></div>
            <div className="form-row">
              <div className="form-group"><label>Start Date</label><input type="date" value={form.startDate} onChange={e => setForm({...form, startDate: e.target.value})} required /></div>
              <div className="form-group"><label>End Date</label><input type="date" value={form.endDate} onChange={e => setForm({...form, endDate: e.target.value})} required /></div>
            </div>
            <div className="form-group"><label>Venue</label><input value={form.venue || ''} onChange={e => setForm({...form, venue: e.target.value})} /></div>
            <div className="form-actions"><button type="button" className="btn-secondary" onClick={() => setShowForm(false)}>Cancel</button><button type="submit" className="btn-primary">{editId ? 'Update' : 'Create'}</button></div>
          </form>
        </div>
      )}
      <div className="card-list">
        {events.map(ev => (
          <div key={ev.id} className="list-card">
            <h3>{ev.name}</h3>
            <p className="meta">{ev.venue} • {ev.startDate} to {ev.endDate}</p>
            <p>{ev.description}</p>
            <span className={`status ${ev.active ? 'active' : 'inactive'}`}>{ev.active ? 'Active' : 'Inactive'}</span>
            {isAdmin && <div style={{marginTop: 8}}><button className="btn-sm" onClick={() => { setForm(ev); setEditId(ev.id!); setShowForm(true); }}>Edit</button><button className="btn-sm danger" onClick={() => { if(confirm('Delete?')) { eventsApi.delete(ev.id!).then(load); } }}>Delete</button></div>}
          </div>
        ))}
      </div>
    </div>
  );
};
export default Events;
