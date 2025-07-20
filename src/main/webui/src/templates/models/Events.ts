type EventType = 'TEMPLATE_CREATED' | 'TEMPLATE_UPDATED' | 'TEMPLATE_DELETED';

interface Owner {
  id: string;
  preferredName: string;
  email: string;
}

interface TemplateCreated {
  id: string;
  name: string;
  description: string;
  owner: Owner;
}

interface Event {
  id: string;
  templateId: string;
  type: EventType;
  content: TemplateCreated;
}

export type {
  EventType,
  Event,
  TemplateCreated
}


